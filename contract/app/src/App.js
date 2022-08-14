import './App.css';
import { Connection, PublicKey } from '@solana/web3.js';
import { Program, AnchorProvider, web3, BN } from '@project-serum/anchor';
import { TokenInstructions } from '@project-serum/serum';
import { getTokenAccount, getMintInfo } from '@project-serum/common';
import idl from './idl.json';
import fs from 'mz/fs';
const anchor = require('@project-serum/anchor');
// const {Keypair} = require("@solana/web3.js");

const opts = {
  // preflightCommitment: "confirmed", // Getting this error "Transaction simulation failed: Blockhash not found "
  preflightCommitment: "finalized"
}
const programID = new PublicKey(idl.metadata.address);

const filename = "/Users/taoyiran/.config/solana/id.json";
const secretKey = Uint8Array.from([
]);
// const resp = web3.Keypair.fromSecretKey(secretKey);
function App() {
  let mint = null;
  let from = null;
  let to = null;

  async function getProvider() {
    const network = "https://api.devnet.solana.com";
    const connection = new Connection(network, opts.preflightCommitment);
    const wallet = window.solana;
    // const wallet = resp;
    console.log("wallet")
    console.log(wallet)
    const provider = new AnchorProvider(
      connection, wallet, opts.preflightCommitment,
    );
    // anchor.getProvider = anchor.Provider.env();
    return provider;
  }

  async function connectWallet() {
    console.log("connect");
    // var data = await fs.readFile(filename, 'utf8');
    // var  config = JSON.parse(data);
    // console.log(config)
    try {
        // process.env.ANCHOR_WALLET = '/Users/taoyiran/.config/solana/id.json';
        const resp = await window.solana.connect();
        // console.log(secretKey)
        // var resp = web3.Keypair.fromSecretKey(secretKey);
        console.log("Connected! Public Key: ", resp.publicKey.toString());
    } catch (err) {
        console.log(err);
        // => { code: 4001, message: 'User rejected the request.' }
    }
  }

  async function disconnectWallet() {
    window.solana.disconnect();
    window.solana.on('disconnect', () => console.log("Disconnected!"));
  }

  async function initializeState() {
    const provider = await getProvider();
    anchor.setProvider(provider);
    var resp = provider.wallet;
    console.log("provider");
    console.log(provider)
    // Takes a few sec per tx
    mint = await createMint(provider);
    // from = await createTokenAccount(provider, mint, resp.publicKey);
    // to = await createTokenAccount(provider, mint, resp.publicKey);

    console.log("-------------------------------------------------------");
    console.log("initializeState");
    console.log("mint ->", mint.toString());
    console.log("from ->", from.toString());
    console.log("to   ->", to.toString());
    console.log("-------------------------------------------------------");
  };

  async function mintToken() {
    const provider = await getProvider();
    console.log(provider);
    const program = new Program(idl, programID, provider);
    console.log(program);

    console.log(mint)

    const tx = await program.rpc.mintToken(new BN(1000), {
      accounts: {
        authority: provider.wallet.publicKey,
        mint:mint,
        tokenAccount: from,
        tokenProgram: TokenInstructions.TOKEN_PROGRAM_ID,
      },
    });

    const fromAccount = await getTokenAccount(provider, from);

    console.log("-------------------------------------------------------");
    console.log("mintToken(mint 1000 tokens)");
    console.log("tx           ->", tx);
    console.log("fromAccount  ->", fromAccount);
    console.log("-------------------------------------------------------");
  }

  // async function transferToken() {
  //   const provider = await getProvider();
  //   const program = new Program(idl, programID, provider);

  //   const tx = await program.rpc.proxyTransfer(new BN(400), {
  //     accounts: {
  //       authority: provider.wallet.publicKey,
  //       from: from,
  //       to: to,
  //       tokenProgram: TokenInstructions.TOKEN_PROGRAM_ID,
  //     },
  //   });

  //   const fromAccount = await getTokenAccount(provider, from);
  //   const toAccount = await getTokenAccount(provider, to);

  //   console.log("-------------------------------------------------------");
  //   console.log("transferToken(fromAccount->toAccount: 400 tokens)");
  //   console.log("tx         ->", tx);
  //   console.log("fromAccount->", fromAccount);
  //   console.log("toAccount  ->", toAccount);
  //   console.log("-------------------------------------------------------");
  // }

  // async function burnToken() {
  //   const provider = await getProvider();
  //   const program = new Program(idl, programID, provider);

  //   const tx = await program.rpc.proxyBurn(new BN(399), {
  //     accounts: {
  //       authority: provider.wallet.publicKey,
  //       mint: mint,
  //       to: to,
  //       tokenProgram: TokenInstructions.TOKEN_PROGRAM_ID,
  //     },
  //   });

  //   const toAccount = await getTokenAccount(provider, to);

  //   console.log("-------------------------------------------------------");
  //   console.log("burnToken(burned 399 tokens from toAccount)");
  //   console.log("tx         ->", tx);
  //   console.log("toAccount  ->", toAccount);
  //   console.log("-------------------------------------------------------");
  // }

  // async function setNewMintAuthority() {
  //   const provider = await getProvider();
  //   const program = new Program(idl, programID, provider);

  //   const newMintAuthority = web3.Keypair.generate();
  //   const tx = await program.rpc.proxySetAuthority(
  //     { mintTokens: {} },
  //     newMintAuthority.publicKey,
  //     {
  //       accounts: {
  //         accountOrMint: mint,
  //         currentAuthority: provider.wallet.publicKey,
  //         tokenProgram: TokenInstructions.TOKEN_PROGRAM_ID,
  //       },
  //     }
  //   );

  //   const mintInfo = await getMintInfo(provider, mint);

  //   console.log("-------------------------------------------------------");
  //   console.log("setNewMintAuthority");
  //   console.log("tx       ->", tx);
  //   console.log("mintInfo ->", mintInfo);
  //   console.log("-------------------------------------------------------");
  // }

  /*-------------------------------------------------------------------------

    functions

  -------------------------------------------------------------------------*/

  // SPL token client boilerplate for test initialization. Everything below here is
  // mostly irrelevant to the point of the example.

  // TODO: remove this constant once @project-serum/serum uses the same version
  //       of @solana/web3.js as anchor (or switch packages).
  const TOKEN_PROGRAM_ID = new web3.PublicKey(
    TokenInstructions.TOKEN_PROGRAM_ID.toString()
  );

  async function createMint(provider, authority) {
    if (authority === undefined) {
      authority = provider.wallet.publicKey;
      console.log("authority")
      console.log(authority.toString())
    }
    anchor.setProvider(provider);
    var resp = provider.wallet;
    const mint = web3.Keypair.generate();
    console.log("mint")
    console.log(mint.publicKey.toString())

    // const ins = web3.SystemProgram.transfer({
    //   fromPubkey:resp.publicKey,
    //   toPubkey:mint.publicKey,
    //   lamports:2616961
    // });

    // const t = new web3.Transaction();
    // t.add(web3.SystemProgram.transfer({
    //   fromPubkey:resp.publicKey,
    //   toPubkey:mint.publicKey,
    //   lamports:2616961
    // }));
    // let result = await provider.send(t, [resp]);
    // console.log(result);

  //     let result = await provider.send(
  //   new web3.Transaction().add(web3.SystemProgram.transfer({
  //     fromPubkey:resp.publicKey,
  //     toPubkey:mint.publicKey,
  //     lamports:2616961
  //   })),
  //   [resp.publicKey]
  // )


  // let connection = new web3.Connection(web3.clusterApiUrl('testnet'));

  let airdropSignature = await provider.connection.requestAirdrop(
    mint.publicKey,
    web3.LAMPORTS_PER_SOL,
  );
  
  await provider.connection.confirmTransaction(airdropSignature);
    
  const instructions = await createMintInstructions(
      provider,
      authority,
      mint.publicKey
    );

    const tx = new web3.Transaction();
    tx.add(...instructions);

    try {
      // const tx_sig = await provider.send(tx, [mint]);
      // const tx_sig = anchor.AnchorProvider.sendAndConfirm(
        const tx_sig = web3.sendAndConfirmTransaction(
        provider.connection,
        tx,
        [mint]
      );
      console.log("mint tx ->", tx_sig);
    } catch (err) {
      console.log("Transaction Error: ", err);
    }

    return mint.publicKey;
  }

  async function createMintInstructions(provider, authority, mint) {
    console.log("createmint")
    let instructions = [
      web3.SystemProgram.createAccount({
        fromPubkey: provider.wallet.publicKey,
        newAccountPubkey: mint,
        space: 82,
        lamports: await provider.connection.getMinimumBalanceForRentExemption(82),
        programId: TOKEN_PROGRAM_ID,
      }),
      // TokenInstructions.initializeMint({
      //   mint,
      //   decimals: 0,
      //   mintAuthority: authority,
      // }),
    ];
    console.log(instructions)
    return instructions;
  }

  async function createTokenAccount(provider, mint, owner) {
    const vault = web3.Keypair.generate();
    const tx = new web3.Transaction();
    tx.add(
      ...(await createTokenAccountInstrs(provider, vault.publicKey, mint, owner))
    );

    try {
      await provider.send(tx, [vault]);
      console.log("account tx ->", tx);

      return vault.publicKey;
    } catch (err) {
      console.log("Transaction Error: ", err);
    }
  }

  async function createTokenAccountInstrs(
    provider,
    newAccountPubkey,
    mint,
    owner,
    lamports
  ) {
    console.log("createInstr")
    var resp = provider.wallet;
    if (lamports === undefined) {
      lamports = await provider.connection.getMinimumBalanceForRentExemption(165);
    }
    return [
      web3.SystemProgram.createAccount({
        fromPubkey: resp.publicKey,
        newAccountPubkey,
        space: 165,
        lamports,
        programId: TOKEN_PROGRAM_ID,
      }),
      TokenInstructions.initializeAccount({
        account: newAccountPubkey,
        mint,
        owner,
      }),
    ];
  }

  return (
    <div className="App">
      <header className="App-header">
        <p>Wait for transaction response with console each action(about 30 sec each tx). If you run it quickly and continuously, it will fail.</p>
        <button onClick={connectWallet}>1. Connect to Wallet</button>
        <button onClick={initializeState}>2. Initialize State(wait for 3 approving)</button>
        <button onClick={mintToken}>3. Mint Token(1 approving)</button>
        {/* <button onClick={transferToken}>4. Transfers Token(1 approving)</button> */}
        {/* <button onClick={burnToken}>5. Burns Token</button> */}
        {/* <button onClick={setNewMintAuthority}>6. Set new mint authority(1 approving)</button> */}
        <button onClick={disconnectWallet}>7. Disconnect</button>
      </header>
    </div>
  );
}

export default App;
