import {refresh} from "../refresh/refresh";
import BufferLayout from "buffer-layout";
import * as Layout from "../lending-client/layout";


async function main(){

    const b = Buffer.from([0x00, 0x65, 0x5b,0x8f,0x64,0x05,0x00,0x00])
    const layout = BufferLayout.struct(
        [Layout.uint64('slot')]
    );
    console.log("amm_open_orders.base_token_total=",layout.decode(b).slot.toString());
    const b1 =  Buffer.from([0x45,0x16,0x8a,0xc7,0x1d,0x00,0x00,0x00])
    console.log("amm_open_orders.quote_token_total=",layout.decode(b1).slot.toString());
   await refresh()
  }

  main()
    .catch(err => {
      console.error(err);
      process.exit(-1);
    })
    .then(() => process.exit());