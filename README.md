# Introduction

This repository contains a simple telemetry contract, but more importantly, it contains a test specific
for [Quorum](https://github.com/jpmorganchase/quorum). The test acts as a sample for a private deployment
of the contract and a private transaction through it and verifies the privacy of the data in this scenario.

# Testing with Quorum

## Dependencies

Install the Quorum 7nodes example network as instructed at https://github.com/jpmorganchase/quorum-examples.

Currently, the code in this repository assumes using the default configurations of the 7nodes network, but if
that is not the case, the node addresses and public keys need to be updated.

```
npm install -g truffle
```

## Running

```
truffle test --network quorum
```

# Testing with testrpc

## Dependencies

```
npm install -g truffle
npm install -g ethereumjs-testrpc
```

## Running

On one terminal window, run:

```
testrcp
```

On another window, run:

```
truffle test
```
