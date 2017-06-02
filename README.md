# Introduction

This repository contains a simple telemetry contract and the source code for a system as shown below:

<img width="1010" alt="architecture" src="https://cloud.githubusercontent.com/assets/207474/26714715/72a999c6-477b-11e7-9156-301e30dce0a0.png">

A video demo showing the code in action can be found from [https://youtu.be/64kMWxTaXw8](https://youtu.be/64kMWxTaXw8).

It also contains a test related to data privacy specific for [Quorum](https://github.com/jpmorganchase/quorum).
The test acts as a sample for a private deployment of the contract and a private transaction through it and verifies the privacy of the data in this scenario.

# Running the automated test on Quorum

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

# Running the automated test on testrpc

<aside class="notice">
Notice that the privacy test fails on purpose, because testrpc doesn't have the required privacy features.
</aside>

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
