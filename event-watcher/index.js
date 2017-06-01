var Web3 = require('web3');
var web3 = new Web3(new Web3.providers.HttpProvider('http://localhost:22006'));

var Telemetry = require('../build/contracts/Telemetry.json');
var telemetryContract = web3.eth.contract(Telemetry.abi);
var address = '0xcaf61ea0a20e648bf43f1d546cf8a315394baf9b';
var telemetryInstance = telemetryContract.at(address);

var telemetryReceived = telemetryInstance.TelemetryReceived();
telemetryReceived.watch(function (error, result) {
  var telemetry = JSON.parse(result.args.telemetry);
  var fistValue = telemetry.values[0];
  console.log('Received ' + telemetry.property + ' value ' + fistValue.value + ' at ' + fistValue.starttime);
});
