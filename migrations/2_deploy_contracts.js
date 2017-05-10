var Telemetry = artifacts.require('./Telemetry.sol');

module.exports = function(deployer) {
  deployer.deploy(Telemetry);
};
