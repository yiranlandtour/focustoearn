import {PayerStore, Store} from "../store";
// import anchor from "@project-serum/anchor";
import {PublicKey} from "@solana/web3.js";

const anchor = require('@project-serum/anchor');

// export const URL = "https://api.devnet.solana.com"
export const URL = "https://api.mainnet-beta.solana.com"
const PROGRAM_ID =  new PublicKey('BJDz4e9rWPnnhDaRp8sEUxvSSvr28zWoZoNGxeeJ5mSS');
export const TEST_BRIDGE_POOL_ID = new PublicKey("FPzdZTXoADaLfBArQkRXQa4TY5T2CwG2Fa59ATbiqyZL");
let program;
export async function getProgram(){
    if (program) {
        return program
    } else {
        process.env.ANCHOR_WALLET=PayerStore.getFilename("id.json")
        process.env.ANCHOR_PROVIDER_URL=URL
        anchor.setProvider(anchor.Provider.env());
        const idl = await new Store().load('idl/raydium_anchor.json', 'utf8');
        return program = new anchor.Program(idl, PROGRAM_ID);
    }

}