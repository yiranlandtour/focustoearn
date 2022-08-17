# focustoearn

<div align="center" id="top">
<h3 align="center">Repository for Focus</h3>
  <p align="center">
    A decentralised application hosted on the Solana
  </p>
</div>

## Vedio
Introduction of [Focus](https://youtu.be/gWXv1P4Xjxw).
## About The Project

you can visit the dapp here: [Focus](). Focus allows readers, writers and web 3 enthusiasts, to create and enjoy community created content, read or write stories and create or sell NFTs along with other features.

### Contract on chain
Our contract is deployed in Devnet.The program address is [6mykzhCRDzJvFdfc6GxUWHgTEG5R6qcBQpY6UfK2Kg8n](https://explorer.solana.com/address/6mykzhCRDzJvFdfc6GxUWHgTEG5R6qcBQpY6UfK2Kg8n?cluster=devnet)

### The Tech Stack

* [React.js](https://reactjs.org/)
* [Rust](https://www.rust-lang.org/)


### Solana Tools

* [Anchor](https://www.anchor-lang.com/)
* [Metaplex](https://www.metaplex.com/)
* [Vipers](https://github.com/saber-hq/vipers)

### Other Tools

* [React Redux](https://react-redux.js.org/)
* [Phantom Android](https://docs.phantom.app/integrating/deeplinks-ios-and-android)



## Design Approach

Focus is built on top of the Solana blockchain - meaning the frontend and backend are both hosted on the blockchain. We use a variety of tools, web2 and web3 alike. The codebase has been opensourced to hopefully be used for educational purposes for any other developers building dapps on the Solana blockchain. 

### Frontend

We try to take a modern approach to the frontend in terms of using modern React practices such as functional components and hooks aswell as implementing the most popular React state management library: Redux. The idea is to make as many readable dynamic components as possible taking a "less is more" approach. We are adament that the dapp works and looks nice across all device screens so we use Native Android for most of the UI design and CSS, This allows custom and fast development of the UI aswell as responsive components.

### Backend

Our backend is a mix between in house smart contracts written in Rust aswell as using frontend api's to call external smart contracts on the Solana blockchain. We use Anchor to build our contract. A large chunk of our Rust backend is calling the Metaplex smart contracts - covering our whole NFT integration. We also have custom smart contracts for managing and storing user data. All of this makes Focus a multi-canister dapp. 

## Solana, Anchor, APIs

The Solana blockchain allows us to create "canisters" which can serve your applications code (a canister is just a smart contract which in turn is just code on a blockchain), We use canisters to host both our frontend and backend. Our dapps authentication uses Solana which is currently an Solana specific authentication system, it is secure and powered by cryptography and allows users to create anonymous user IDs and wallets. We have integrated the Metaplex Protocol which allows us to achieve NFT integration aswell as providing us with a variety of tools. We use UserGeek for analytics which allows us to obtain stats on unique users who use our dapp aswell as things like how many users purchased NFTs, All information is anonymous. 

## Anti cheating
What if people just open the app, but don't do the task.If people leave the app, the time stop and the task fail.
What if people use two or more phones to cheat for tokens.We need people to move some steps or using the phone for some time.
## Roadmap

- [x] User launched on the Solana blockchain
- [x] NFT Inventory
- [x] NFT Launchpad
- [x] NFT Marketplace
- [ ] Anti cheating System
- [ ] User Activity
    - [x] Focus on a Task 
    - [x] Finish the Task to get tokens
    - [x] Burn tokens to Plant a Tree
    - [x] Mint NFTs from your land
    - [ ] Soul Bound Token 
    - [ ] Stole fruit From Other user's tree
    - [ ] Buy land to plant more trees

## Cooperation
    yiranlandtour1@gmail.com
<p align="right">(<a href="#top">back to top</a>)</p>
