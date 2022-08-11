import { TransactionInstruction, PublicKey, AccountMeta } from "@solana/web3.js" // eslint-disable-line @typescript-eslint/no-unused-vars
import BN from "bn.js" // eslint-disable-line @typescript-eslint/no-unused-vars
import * as borsh from "@project-serum/borsh" // eslint-disable-line @typescript-eslint/no-unused-vars
import { PROGRAM_ID } from "../programId"

export interface MintNftArgs {
  creatorKey: PublicKey
  uri: string
  title: string
}

export interface MintNftAccounts {
  mintAuthority: PublicKey
  mint: PublicKey
  tokenProgram: PublicKey
  metadata: PublicKey
  tokenAccount: PublicKey
  tokenMetadataProgram: PublicKey
  payer: PublicKey
  systemProgram: PublicKey
  rent: PublicKey
  masterEdition: PublicKey
}

export const layout = borsh.struct([
  borsh.publicKey("creatorKey"),
  borsh.str("uri"),
  borsh.str("title"),
])

export function mintNft(args: MintNftArgs, accounts: MintNftAccounts) {
  const keys: Array<AccountMeta> = [
    { pubkey: accounts.mintAuthority, isSigner: true, isWritable: true },
    { pubkey: accounts.mint, isSigner: false, isWritable: true },
    { pubkey: accounts.tokenProgram, isSigner: false, isWritable: false },
    { pubkey: accounts.metadata, isSigner: false, isWritable: true },
    { pubkey: accounts.tokenAccount, isSigner: false, isWritable: true },
    {
      pubkey: accounts.tokenMetadataProgram,
      isSigner: false,
      isWritable: false,
    },
    { pubkey: accounts.payer, isSigner: false, isWritable: true },
    { pubkey: accounts.systemProgram, isSigner: false, isWritable: false },
    { pubkey: accounts.rent, isSigner: false, isWritable: false },
    { pubkey: accounts.masterEdition, isSigner: false, isWritable: true },
  ]
  const identifier = Buffer.from([211, 57, 6, 167, 15, 219, 35, 251])
  const buffer = Buffer.alloc(1000)
  const len = layout.encode(
    {
      creatorKey: args.creatorKey,
      uri: args.uri,
      title: args.title,
    },
    buffer
  )
  const data = Buffer.concat([identifier, buffer]).slice(0, 8 + len)
  const ix = new TransactionInstruction({ keys, programId: PROGRAM_ID, data })
  return ix
}
