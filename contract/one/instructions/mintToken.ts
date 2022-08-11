import { TransactionInstruction, PublicKey, AccountMeta } from "@solana/web3.js" // eslint-disable-line @typescript-eslint/no-unused-vars
import BN from "bn.js" // eslint-disable-line @typescript-eslint/no-unused-vars
import * as borsh from "@project-serum/borsh" // eslint-disable-line @typescript-eslint/no-unused-vars
import { PROGRAM_ID } from "../programId"

export interface MintTokenAccounts {
  mint: PublicKey
  tokenProgram: PublicKey
  tokenAccount: PublicKey
  authority: PublicKey
}

export function mintToken(accounts: MintTokenAccounts) {
  const keys: Array<AccountMeta> = [
    { pubkey: accounts.mint, isSigner: false, isWritable: true },
    { pubkey: accounts.tokenProgram, isSigner: false, isWritable: false },
    { pubkey: accounts.tokenAccount, isSigner: false, isWritable: true },
    { pubkey: accounts.authority, isSigner: false, isWritable: true },
  ]
  const identifier = Buffer.from([172, 137, 183, 14, 207, 110, 234, 56])
  const data = identifier
  const ix = new TransactionInstruction({ keys, programId: PROGRAM_ID, data })
  return ix
}
