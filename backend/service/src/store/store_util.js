import {Account, Connection, PublicKey} from "@solana/web3.js";
import {LendingMarket} from "../lending-client/LendingMarket";
import {TOKEN_PROGRAM_ID} from "@solana/spl-token";

import {PayerStore, Store} from './index';
import {url} from "../util/url";
import {Reserve} from "../lending-client/Reserve";
import {Token} from "../token-client/token";


const PAYER_CONFIG_PATH = 'id.json';
const VOTE_PAYER_CONFIG_PATH = "vote-payer.json"
// const CONFIG_PATH = 'config-product.json';
const CONFIG_PATH = 'config-test.json';
export const LENDING_PROGRAM_ID = new PublicKey(
  // "BDBsJpBPWtMfTgxejekYCWUAJu1mvQshiwrKuTjdEeT3"//mainnet test
  "7Zb1bGi32pfsrBkzWdqd4dFhUXwp5Nybr1zuaEwN34hy" // mainnet production
);

let store = new Store();
let payerStore = new PayerStore();
let payer: Account;
let connection: Connection;
let config:Object;
let marketResult:Object;
let votePayer: Account;
let voteNewUser = []

export async function init(){
    voteNewUser = await store.load("vote-voter.json")
}
export function getUser(){
    return voteNewUser[0]
}
export async function getNewUser(token:Token,voteConfig){
    let newAccount = new Account()
    let config = {}
    config["pubkey"] = newAccount.publicKey.toString()
    config["secretKey"] = newAccount.secretKey.toJSON().data
    let usdcAccount = await token.createAssociatedTokenAccount(newAccount.publicKey)
    config["usdcAccount"] = usdcAccount.toString()
    config["usdcAccountPubkey"] = usdcAccount.toString()
    voteNewUser.push(config)
    return config
}
export async function saveVoter(){
    await store.save("vote-voter.json",voteNewUser)
}
export async function saveLarix(secretKey,publicKey){
    await store.save("larxi_secret_key.json",secretKey)
    await store.save("larxi_public_key.json",publicKey)
}
export async function getConfig():Promise<Object>{
    if (config){
        return config
    }
    try {
        return store.load(CONFIG_PATH);
    } catch (e){
        console.error(e)
    }
}
export async function getPayer():Promise<Account>{
    if (payer){
        return payer
    }
    try {
        let payer_store = await payerStore.load(PAYER_CONFIG_PATH);
        payer = new Account(payer_store);
        console.log("payer=",payer.publicKey.toString());
        return payer
    } catch (e){
        console.error(e)
        let payer = new Account();
        await payerStore.save(PAYER_CONFIG_PATH,payer.secretKey.toJSON().data)
        return payer
    }
}
export async function getVotePayer():Promise<Account>{
    if (votePayer){
        return votePayer
    }
    try {
        let votePayer = await store.load(VOTE_PAYER_CONFIG_PATH);
        let payer = new Account(votePayer["secretKey"])
        votePayer["usdcAccountPubkey"] = new PublicKey(votePayer["usdcAccount"])
        votePayer["payer"] = payer
        return votePayer
    } catch (e){
        console.error(e)
        // let payer = new Account();
        // let payerConfig = {}
        // payerConfig["pubkey"] = payer.publicKey.toString()
        // payerConfig["secretKey"] = payer.secretKey.toJSON().data
        // await store.save(VOTE_PAYER_CONFIG_PATH,payerConfig)
        return payer
    }
}
export async function getConnection(): Promise<Connection> {
    if (connection) return connection;
    connection = new Connection(url, 'recent');
    const version = await connection.getVersion();

    console.log('Connection to cluster established:', url, version);
    return connection;
}
export async function getConfirmedConnection(): Promise<Connection> {
    if (connection) return connection;
// | 'processed'
//     | 'confirmed'
//     | 'finalized'
//     | 'recent'
//     | 'single'
//     | 'singleGossip'
//     | 'root'
//     | 'max';
    connection = new Connection(url, 'confirmed');
    const version = await connection.getVersion();

    console.log('Connection to cluster established:', url, version);
    return connection;
}
export async function getLendingMarket():Promise<LendingMarket>{
    let payer = await getPayer();
    let marketResult = await getMarketResult();
    let lendingMarketAccount = new Account(marketResult["account"]);
    return  new LendingMarket({
        connection:await getConnection(),
        tokenProgramId: TOKEN_PROGRAM_ID,
        lendingProgramId: LENDING_PROGRAM_ID,
        lendingMarketAccount:lendingMarketAccount,
        lendingMarketOwner:payer,
        payer:payer
    })
}
export async function getMarketResult():Promise<Object>{
    if (marketResult){
        return marketResult
    }
    return marketResult = await store.load((await getConfig())["file"])
}

export async function saveLendingMarket(lendingMarket: LendingMarket,marketResult) {
    let configMarket = await getConfig()
    let lendingMarketConfig={}
    lendingMarketConfig["account"] = lendingMarket.account.secretKey.toJSON().data
    lendingMarketConfig["account_pubkey"] = lendingMarket.account.publicKey.toString()
    lendingMarketConfig["mine_mint_id"] = lendingMarket.mine_mint_id
    lendingMarketConfig["mine_supply_id"] = lendingMarket.mine_supply_id
    let reservesConfig
    if (marketResult !== undefined && marketResult.reserves !==undefined){
        reservesConfig = marketResult.reserves
    } else {
        reservesConfig = {}
    }
    if (lendingMarket.reserves!==undefined){
        for (let reserve of lendingMarket.reserves){
            let reserveConfig:Map = new Map()
            let reserveParam = reserve.reserveParams;
            reserveConfig["reserveId"]=reserveParam.reserveAccount.publicKey.toString()
            reserveConfig["liquidityTokenID"]=reserveParam.liquidityTokenID.toString()
            reserveConfig["liquiditySupplyID"]=reserveParam.liquiditySupply.publicKey.toString()
            reserveConfig["liquidityFeeReceiverID"]=reserveParam.liquidityFeeReceiver.toString()
            reserveConfig["collateralTokenID"]=reserveParam.collateralToken.publicKey.toString()
            reserveConfig["collateralSupplyID"]=reserveParam.collateralSupply.publicKey.toString()
            reserveConfig["lendingMarketID"]=reserveParam.lendingMarket.publicKey.toString()
            reserveConfig["lendingMarketAuthority"]=reserveParam.lendingMarketAuthority.toString()
            reserveConfig["lendingMarketOwnerAccount"]=reserveParam.lendingMarketOwnerAccount.publicKey.toString()
            reserveConfig["bridgePoolID"] = reserveParam.isLp?reserveParam.bridgePoolConfig.bridgePool.toString():""
            reserveConfig["bridgeFarmLedgerID"] = reserveParam.isLp?reserveParam.bridgePoolConfig.farmLedger.toString():""
            reserveParam["bridgeLpSupply"] = reserveParam.isLp?reserveParam.bridgePoolConfig.lpSupply.toString():""
            reserveParam["bridgeCoinSupply"] = reserveParam.isLp?reserveParam.bridgePoolConfig.lpSupply.toString():""
            reserveParam["bridgePcSupply"] = reserveParam.isLp?reserveParam.bridgePoolConfig.lpSupply.toString():""
            reservesConfig[reserve.reserveParams.liquidityTokenID.toString()]=reserveConfig;
        }
    }
    // console.log(reservesConfig)
    lendingMarketConfig['reserves']=reservesConfig
    await store.save(configMarket["file"],lendingMarketConfig)
}
