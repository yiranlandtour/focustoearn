import { TransactionInstruction, PublicKey, AccountMeta } from "@solana/web3.js" // eslint-disable-line @typescript-eslint/no-unused-vars
import BN from "bn.js" // eslint-disable-line @typescript-eslint/no-unused-vars
import * as borsh from "@project-serum/borsh" // eslint-disable-line @typescript-eslint/no-unused-vars
import { PROGRAM_ID } from "../programId"

export interface TransferTokenAccounts {
  tokenProgram: PublicKey
  from: PublicKey
  to: PublicKey
  fromAuthority: PublicKey
}

export function transferToken(accounts: TransferTokenAccounts) {
  const keys: Array<AccountMeta> = [
    { pubkey: accounts.tokenProgram, isSigner: false, isWritable: false },
    { pubkey: accounts.from, isSigner: false, isWritable: true },
    { pubkey: accounts.to, isSigner: false, isWritable: true },
    { pubkey: accounts.fromAuthority, isSigner: true, isWritable: false },
  ]
  const identifier = Buffer.from([219, 17, 122, 53, 237, 171, 232, 222])
  const data = identifier
  const ix = new TransactionInstruction({ keys, programId: PROGRAM_ID, data })
  return ix
}
