const Telemetry = artifacts.require('./Telemetry.sol');

const sampleTelemetry = {
  values: [
    {
      value: 99
    }
  ]
};

const testrpc = web3.version.node.includes('EthereumJS TestRPC');

const nodes = {
  node2: {
    url: testrpc ? 'http://localhost:8545' : 'http://localhost:22001',
    publicKey: 'QfeDAys9MPDs2XHExtc84jKGHxZg/aj52DTh0vtA3Xc='
  },
  node3: {
    url: testrpc ? 'http://localhost:8545' : 'http://localhost:22002',
    publicKey: '1iTZde/ndBHvzhcl7V68x44Vx7pl8nwx9LqnM/AfJUg='
  },
  node7: {
    url: testrpc ? 'http://localhost:8545' : 'http://localhost:22006',
    publicKey: 'ROAZBWtSacxXQrOe3FGAqJDyJjFePR5ce4TSIzmJ0Bc='
  },
};

const setProvider = function (instance, url) {
  const newProvider = new web3.providers.HttpProvider(url);
  web3.setProvider(newProvider);
  instance.contract._eth._requestManager.provider = newProvider;
};

contract('Telemetry', function (accounts) {
  const fromAccount = accounts[0];

  it('should get an event when telemetry sent', function (done) {
    Telemetry.deployed().then(function (instance) {
      const telemetryReceived = instance.TelemetryReceived();
      telemetryReceived.watch(function (error, result) {
        telemetryReceived.stopWatching();

        const telemetry = JSON.parse(result.args.telemetry);
        assert.equal(telemetry.values[0].value, sampleTelemetry.values[0].value,
          'the event should have same value as in the sent telemetry');

        done();
      });
      instance.sendTelemetry(JSON.stringify(sampleTelemetry), {
        from: fromAccount,
        privateFor: [nodes.node7.publicKey]
      });
    });
  });

  it('should not be able to get private telemetry', function (done) {
    Telemetry.deployed().then(function (instance) {
      const resultChecker = function (result) {
        // The result is checked with a retry loop, because the transaction is initially
        // written via node2 and it takes a while until the data is synced and readable
        // via node7 (which is used for checking the final result since node7 public key
        // is used as the privateFor value in the transaction and when contract was deployed).
        const latestTelemetry = JSON.parse(result);
        if (latestTelemetry.error === 'Not Found') {
          setTimeout(function () {
            instance.getLatestTelemetry.call(fromAccount).then(resultChecker);
          }, 1000);
          return;
        }
        assert.equal(latestTelemetry.values[0].value, sampleTelemetry.values[0].value,
          'node 7 should have access to the private telemetry');
        done();
      };

      // Send the telemetry via node2.
      setProvider(instance, nodes.node2.url);
      instance.sendTelemetry.sendTransaction(JSON.stringify(sampleTelemetry), {
        from: fromAccount,
        privateFor: [nodes.node7.publicKey]
      })
      .then(function (tx) {
        return instance.sendTelemetry.sendTransaction(JSON.stringify(sampleTelemetry), {
          from: fromAccount,
          privateFor: [nodes.node7.publicKey]
        });
      })
      .then(function (tx) {
        setProvider(instance, nodes.node3.url);
        return instance.getLatestTelemetry.call(fromAccount);
      })
      .catch(function (error) {
        // This catch is here, because Quorum release v1.1.0 has
        // an issue which causes an error when making an unauthorized
        // call to a private contract and when string is expected as
        // return value.
        assert(error.name, 'BigNumber Error', 'a specific error is expected');
        // Return empty string to "simulate" what should be returned after
        // the issue in Quorum is fixed.
        return '';
      })
      .then(function (result) {
        // The expectation is that we should end up here, because
        // node3 does not have permissions to call the contract
        // deployed privately for other nodes.
        assert.equal(result, '',
          'node 3 should not have access to the private telemetry');
        setProvider(instance, nodes.node7.url);
        return instance.getLatestTelemetry.call(fromAccount);
      })
      .then(resultChecker);
    });
  });

});
