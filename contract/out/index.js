import { Connection, PublicKey } from '@solana/web3.js';
import { Program, Provider, web3, BN } from '@project-serum/anchor';
import { TokenInstructions } from '@project-serum/serum';
import { getTokenAccount, getMintInfo } from '@project-serum/common';
import idl from './idl.json';
import fs from 'mz/fs';
const anchor = require('@project-serum/anchor');


const opts = {
    // preflightCommitment: "confirmed", // Getting this error "Transaction simulation failed: Blockhash not found "
    preflightCommitment: "finalized"
  }
  const programID = new PublicKey(idl.metadata.address);
  
  const filename = "/Users/taoyiran/.config/solana/id.json";
  const secretKey = Uint8Array.from([
    113,89,165,168,124,188,190,71,55,171,193,6,197,32,34,226,51,25,177,53,116,163,44,166,251,115,202,177,187,81,34,138,211,180,157,163,207,209,16,156,181,171,211,109,187,28,144,72,52,117,16,206,149,85,42,105,68,246,124,11,25,72,105,244
  ]);


  async function main() {
    await test();
  }
  
  main()
    .catch(err => {
      console.error(err);
      process.exit(-1);
    })
    .then(() => process.exit());
  
  export async function test() {
    
    console.log("nima")
}