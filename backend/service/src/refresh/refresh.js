import {getProgram} from "../util/util";
import {PublicKey} from "@solana/web3.js";
import json from "@rollup/plugin-json";
export async function refresh(){

    const program = await getProgram()
    const bridgePoolID = new PublicKey("FPzdZTXoADaLfBArQkRXQa4TY5T2CwG2Fa59ATbiqyZL")
    let bridgePool = await program.account.pool.fetch(bridgePoolID)

    console.log("base",bridgePool.base.toString())
    console.log("owner",bridgePool.owner.toString())
    console.log("pendingOwner",bridgePool.pendingOwner.toString())
    console.log("ammId",bridgePool.ammId.toString())
    console.log("lpMint",bridgePool.lpMint.toString())
    console.log("lpSupply",bridgePool.lpSupply.toString())
    console.log("coinSupply",bridgePool.coinSupply.toString())
    console.log("pcSupply",bridgePool.pcSupply.toString())
    console.log("addLpWithdrawAmountAuthority",bridgePool.addLpWithdrawAmountAuthority.toString())
    console.log("coinMintPrice",bridgePool.pcMintPrice.toString())
    console.log("ammOpenOrders",bridgePool.ammOpenOrders.toString())
    console.log("ammCoinMintSupply",bridgePool.ammCoinMintSupply.toString())
    console.log("ammPcMintSupply",bridgePool.ammPcMintSupply.toString())
    console.log("farmPoolId",bridgePool.farmPoolId.toString())
    console.log("farmLedger",bridgePool.farmLedger.toString())
    console.log("rewardSupply[0]",bridgePool.rewardSupply[0].toString())
    console.log("swapRouter[0]",bridgePool.swapRouter[0].toString())




    console.log(bridgePool.lastUpdateSlot.toString());
    console.log(bridgePool.lpPrice.toString());
    console.log(bridgePool.lpPriceExpo.toString());
    console.log(
        bridgePool.lpPrice.toString().slice(0,bridgePool.lpPrice.toString().length-bridgePool.lpPriceExpo)
        +"."
        + bridgePool.lpPrice.toString().slice(-bridgePool.lpPriceExpo)
    )

    let result = await program.rpc.refresh(
        {
            accounts:{
                pool: bridgePoolID,
                ammId: bridgePool.ammId,
                lpMint: bridgePool.lpMint,
                lpSupply: bridgePool.lpSupply,
                coinMintPrice: bridgePool.coinMintPrice,
                pcMintPrice:bridgePool.pcMintPrice,
                ammOpenOrders:bridgePool.ammOpenOrders,
                coinMintSupply:bridgePool.ammCoinMintSupply,
                pcMintSupply:bridgePool.ammPcMintSupply,
                farmLedger:bridgePool.farmLedger,

            }
        }
    )
    console.log(result);
    bridgePool = await program.account.pool.fetch(bridgePoolID)
    console.log(bridgePool.lastUpdateSlot.toString());
    console.log(bridgePool.lpPrice.toString());
    console.log(bridgePool.lpPriceExpo.toString());
    console.log(
        bridgePool.lpPrice.toString().slice(0,bridgePool.lpPrice.toString().length-bridgePool.lpPriceExpo)
        +"."
        + bridgePool.lpPrice.toString().slice(-bridgePool.lpPriceExpo)
    )
}