pragma solidity ^0.4.4;

contract Telemetry {
  mapping(address => string) latestTelemetry;

  event TelemetryReceived(address from, string telemetry);

  function sendTelemetry(string telemetry) {
    latestTelemetry[msg.sender] = telemetry;
    TelemetryReceived(msg.sender, telemetry);
  }

  function getLatestTelemetry(address addr) returns (string) {
    if (bytes(latestTelemetry[addr]).length == 0) {
      return "{ \"error\": \"Not Found\" }";
    } else {
      return latestTelemetry[addr];
    }
  }
}
