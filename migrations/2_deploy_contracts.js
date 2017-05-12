var Telemetry = artifacts.require('./Telemetry.sol');

module.exports = function(deployer) {
  deployer.deploy(Telemetry, {
    privateFor: ['ROAZBWtSacxXQrOe3FGAqJDyJjFePR5ce4TSIzmJ0Bc=']
  });
};
