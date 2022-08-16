// @flow

import {Account, Connection} from '@solana/web3.js';

import {sleep} from './sleep';

/**
 * 创建一个Account并拉取一点空投
 * @param connection
 * @param lamports
 * @returns {Promise<module:"@solana/web3.js".Account>}
 */
export async function newAccountWithLamports(
  connection: Connection,
  lamports: number = 1000000,
): Promise<Account> {
  const account = new Account();

  let retries = 30;
  await connection.requestAirdrop(account.publicKey, lamports);
  for (;;) {
    await sleep(500);
    if (lamports == (await connection.getBalance(account.publicKey))) {
      return account;
    }
    if (--retries <= 0) {
      break;
    }
  }
  throw new Error(`Airdrop of ${lamports} failed`);
}
