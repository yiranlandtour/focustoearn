// @flow
import {
    Account,
    Connection, Keypair,
    PublicKey
  } from '@solana/web3.js';
  import { InterestBearingMintConfigStateLayout, TOKEN_PROGRAM_ID,getAssociatedTokenAddress,createInitializeMintInstruction ,MINT_SIZE,createAssociatedTokenAccountInstruction} from '@solana/spl-token';
  import BufferLayout from 'buffer-layout';
//   import BN from 'bn.js';
  
  import {
    getConnection,
    getConfig,
    getPayer,
    LENDING_PROGRAM_ID,
    saveLendingMarket, getLendingMarket, getMarketResult
  } from '../store/store_util';
  import {Store} from '../store/index';


//   import { Connection, PublicKey } from '@solana/web3.js';
import { Program, AnchorProvider, web3, BN } from '@project-serum/anchor';
import { TokenInstructions } from '@project-serum/serum';
import { getTokenAccount, getMintInfo } from '@project-serum/common';
import idl from './contract.json';
// import fs from 'mz/fs';
const anchor = require('@project-serum/anchor');
  
  var provider;

  let mint = null;
  let from = null;
  let to = null;
  let one = null;
  let he = new PublicKey("3RaLFRSay1mu1ppHvKASLvVjZ6XzWfy7xRDsEupq36xE")
  const TOKEN_METADATA_PROGRAM_ID = new PublicKey(
    "metaqbxxUerdq28cj1RbAWkYQm3ybzjb6a8bt518x1s"
  );
  const opts = {
    // preflightCommitment: "confirmed", // Getting this error "Transaction simulation failed: Blockhash not found "
    preflightCommitment: "finalized"
  }
  const programId = new PublicKey('6mykzhCRDzJvFdfc6GxUWHgTEG5R6qcBQpY6UfK2Kg8n');
  const network = "https://api.devnet.solana.com";
  const connection = new web3.Connection(web3.clusterApiUrl('devnet'), 'confirmed');
  var payer;
  async function getProvider() {
    const network = "https://api.devnet.solana.com";
    const connection = new Connection(network, opts.preflightCommitment);
    const wallet = payer;
    // const wallet = resp;
    console.log("wallet")
    console.log(wallet)
    const provider = new AnchorProvider(
      connection, wallet, opts.preflightCommitment,
    );
    return provider;
  }

  export async function initf(){

    process.env.ANCHOR_WALLET="/Users/taoyiran/.config/solana/id.json"
    process.env.ANCHOR_PROVIDER_URL="https://api.devnet.solana.com"
    anchor.getProvider(anchor.AnchorProvider.env());
    payer = await getPayer();
    console.log("init")
    // mint  = await createMint(anchor.provider);
    // from = await createTokenAccount(anchor.provider, mint, payer.publicKey);
    // to = await createTokenAccount(anchor.provider, mint, payer.publicKey);
    // console.log("fromto");
    // console.log(from.toString());
    // console.log(to.toString());
    mint = new PublicKey("6BsoNXHypm9i86TZ2pbRMrgEB8mWwdfezh23N7nUCrgh");
    from = new PublicKey("F6AsTuW3y4NCM39oJU8ms2suK8RbpTZd9fkbuYirfjjd");
    to = new PublicKey("5KRE39yyQCp6uZdAeaxFn5MKfrH6sjrCuAf6paMGoXBS");
    // one = await createTokenAccount(anchor.provider,mint,he);

    // await mintToken();
    await mintNFT();
  }


  
 
  
  async function createMint(provider, authority) {
    // var payer = await getPayer();
    if (authority === undefined) {
    //   authority = provider.wallet.publicKey;
      authority = payer.publicKey;
    //   console.log("authority")
    //   console.log(authority)
    }
    anchor.setProvider(provider);
    // var resp = provider.wallet;
    const mint = web3.Keypair.generate();
    console.log("mint")
    console.log(mint.publicKey.toString())






    // let airdropSignature = await connection.requestAirdrop(
    //     mint.publicKey,
    //     web3.LAMPORTS_PER_SOL,
    // );
    // console.log(airdropSignature);
    // await connection.confirmTransaction(airdropSignature);
  

  const instructions = await createMintInstructions(

      authority,
      mint.publicKey
    );



    var tx = new web3.Transaction();
    tx.add(...instructions);
    // tx.add(web3.SystemProgram.createAccount({
    //     fromPubkey: payer.publicKey,
    //     newAccountPubkey: mint.publicKey,
    //     space: 82,
    //     lamports: await connection.getMinimumBalanceForRentExemption(82),
    //     programId: TOKEN_PROGRAM_ID,
    //   }));
    
        var tx_sig = await web3.sendAndConfirmTransaction(
        connection,
        tx,
        [payer,mint]
      );


      console.log("mint tx ->", tx_sig);
    return mint.publicKey;
  }

  async function createMintInstructions( authority, mint) {
    // console.log("createmint")
    // console.log(TOKEN_PROGRAM_ID.toString());
    // console.log(authority.toString());
    let instructions = [
      web3.SystemProgram.createAccount({
        fromPubkey: payer.publicKey,
        newAccountPubkey: mint,
        space: 82,
        lamports: await connection.getMinimumBalanceForRentExemption(82),
        programId: TOKEN_PROGRAM_ID,
      }),
      TokenInstructions.initializeMint({
        mint,
        decimals: 0,
        mintAuthority: authority,
      }),
    ];
    // console.log(instructions)
    return instructions;
  }

  async function createTokenAccount(provider, mint, owner) {
    const vault = web3.Keypair.generate();
    const tx = new web3.Transaction();
    tx.add(
      ...(await createTokenAccountInstrs(provider, vault.publicKey, mint, owner))
    );

    try {
    //   await provider.send(tx, [vault]);
      var tx_sig = await web3.sendAndConfirmTransaction(
        connection,
        tx,
        [payer,vault]
      );
      console.log("account tx ->", tx_sig);

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
    // var resp = provider.wallet;
    if (lamports === undefined) {
      lamports = await connection.getMinimumBalanceForRentExemption(165);
    }
    return [
      web3.SystemProgram.createAccount({
        fromPubkey: payer.publicKey,
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

  async function mintToken() {
    const provider = await getProvider();
    // console.log(provider);
    const program = new Program(idl, programId, provider);
    // console.log(program);


    const transaction = new web3.Transaction().add(
        program.transaction.mintToken(new BN(1000), {
            accounts: {
              authority: payer.publicKey,
              mint:mint,
              tokenAccount: from,
              tokenProgram: TokenInstructions.TOKEN_PROGRAM_ID,
            },
          }));
    var tx_sig = await web3.sendAndConfirmTransaction(
        connection,
        transaction,
        [payer]
      );


    console.log("tx           ->", tx_sig);
    // const fromAccount = await getTokenAccount(provider, from);

    console.log("-------------------------------------------------------");
    console.log("mintToken(mint 1000 tokens)");
    // console.log("tx           ->", tx);
    // console.log("fromAccount  ->", fromAccount);
    console.log("-------------------------------------------------------");
  }

async function mintNFT(){
    const provider = await getProvider();
    // console.log(provider);
    const program = new Program(idl, programId, provider);
    // console.log(program);
    const lamports =
      await program.provider.connection.getMinimumBalanceForRentExemption(
        MINT_SIZE
      );
    const mintKey = web3.Keypair.generate();
    const NftTokenAccount = await getAssociatedTokenAddress(
        mintKey.publicKey,
      payer.publicKey
    );
    console.log("NFT Account: ", NftTokenAccount.toBase58());
    console.log(NftTokenAccount);


    const mint_tx = new web3.Transaction().add(
      web3.SystemProgram.createAccount({
        fromPubkey: payer.publicKey,
        newAccountPubkey: mintKey.publicKey,
        space: MINT_SIZE,
        programId: TOKEN_PROGRAM_ID,
        lamports,
      }),
      createInitializeMintInstruction(
        mintKey.publicKey,
        0,
        payer.publicKey,
        payer.publicKey
      ),
      createAssociatedTokenAccountInstruction(
        payer.publicKey,
        NftTokenAccount,
        payer.publicKey,
        mintKey.publicKey
      )
    );

    // const res = await program.provider.sendAndConfirm(mint_tx, [mintKey]);
    var res = await web3.sendAndConfirmTransaction(
      connection,
      mint_tx,
      [payer,mintKey]
    );
    console.log("Account: ", res);
    console.log("Mint key: ", mintKey.publicKey.toString());


    const metadataAddress =   await web3.PublicKey.findProgramAddress(
        [
          Buffer.from("metadata"),
          TOKEN_METADATA_PROGRAM_ID.toBuffer(),
          mintKey.publicKey.toBuffer(),
        ],
        TOKEN_METADATA_PROGRAM_ID
      );
        // console.log(metadataAddress[0]);
    const masterEdition = await web3.PublicKey.findProgramAddress(
        [
          Buffer.from("metadata"),
          TOKEN_METADATA_PROGRAM_ID.toBuffer(),
          mintKey.publicKey.toBuffer(),
          Buffer.from("edition"),
        ],
        TOKEN_METADATA_PROGRAM_ID
      );
      // console.log(masterEdition[0].toString());
     var  accountinfo = {
      mintAuthority: payer.publicKey,
      mint: mintKey.publicKey,
      tokenAccount: NftTokenAccount,
      tokenProgram: TOKEN_PROGRAM_ID,
      metadata: metadataAddress[0],
      tokenMetadataProgram: TOKEN_METADATA_PROGRAM_ID,
      payer: payer.publicKey,
      systemProgram: web3.SystemProgram.programId,
      rent: web3.SYSVAR_RENT_PUBKEY,
      masterEdition: masterEdition[0],
  };
  console.log(accountinfo);
    const transaction = new web3.Transaction().add(
        program.transaction.mintNft(      
            mintKey.publicKey,
            "https://arweave.net/y5e5DJsiwH0s_ayfMwYk-SnrZtVZzHLQDSTZ5dNRUHA",
            "NFT Title", {
            accounts: accountinfo,
          }));
        // console.log(transaction);
    var tx_sig = await web3.sendAndConfirmTransaction(
        connection,
        transaction,
        [payer]
      );
      console.log(tx_sig);
}

// const getMetadata = async (
//     mint: anchor.web3.PublicKey
//   ): Promise<anchor.web3.PublicKey> => {
//     return (
//       await anchor.web3.PublicKey.findProgramAddress(
//         [
//           Buffer.from("metadata"),
//           TOKEN_METADATA_PROGRAM_ID.toBuffer(),
//           mint.toBuffer(),
//         ],
//         TOKEN_METADATA_PROGRAM_ID
//       )
//     )[0];
//   };

//   const getMasterEdition = async (
//     mint: web3.PublicKey
//   ): Promise<anchor.web3.PublicKey> => {
//     return (
//       await anchor.web3.PublicKey.findProgramAddress(
//         [
//           Buffer.from("metadata"),
//           TOKEN_METADATA_PROGRAM_ID.toBuffer(),
//           mint.toBuffer(),
//           Buffer.from("edition"),
//         ],
//         TOKEN_METADATA_PROGRAM_ID
//       )
//     )[0];
//   };