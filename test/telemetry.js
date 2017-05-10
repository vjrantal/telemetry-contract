const Telemetry = artifacts.require('./Telemetry.sol');

const sampleTelemetry = {
  values: [
    {
      value: 99
    }
  ]
};

contract('Telemetry', function (accounts) {
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
      instance.sendTelemetry(JSON.stringify(sampleTelemetry));
    });
  });
});
