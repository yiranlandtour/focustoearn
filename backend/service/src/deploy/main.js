import {
  initf,

} from './init.js';
import {Account} from '@solana/web3.js';



async function main(){

  await initf();
}

main()
  .catch(err => {
    console.error(err);
    process.exit(-1);
  })
  .then(() => process.exit());