// import * as anchor from "@project-serum/anchor";
// import { Program } from "@project-serum/anchor";
// import { Contract } from "../target/types/contract";

// describe("contract", () => {
//   // Configure the client to use the local cluster.
//   anchor.setProvider(anchor.AnchorProvider.env());

//   const program = anchor.workspace.Contract as Program<Contract>;

//   it("Is initialized!", async () => {
//     // Add your test here.
//     const tx = await program.methods.initialize().rpc();
//     console.log("Your transaction signature", tx);
//   });
// });


// import * as anchor from '@project-serum/anchor';
// import { Program } from '@project-serum/anchor';
// import { Contract } from '../target/types/contract';
// describe('counterapp', () => {    
//     const provider = anchor.Provider.env()
// anchor.setProvider(provider);    
// const program = anchor.workspace.Contract as Program<Contract>;    
// const counterAccount = anchor.web3.Keypair.generate();    
// it('Is initialized!', async () => {
//         await program.rpc.create({
//             accounts: {
//                 counterAccount: counterAccount.publicKey,
//                 user: provider.wallet.publicKey,
//                 systemProgram: anchor.web3.SystemProgram.programId,
//             },
//             signers: [counterAccount]
//         } as any)
// });    
// it("Increment counter", async () => {
//         await program.rpc.increment({
//             accounts: {
//                 counterAccount: counterAccount.publicKey
//             }
//         } as any)
// })    
// it("Fetch account", async () => {
//         const account: any = await
//         program.account.counterAccount.fetch(counterAccount.publicKey)
//         console.log(account.count)
//     })
// });
