import {
  LIQUIDITY_POOL_PROGRAM_ID_V2,
  LIQUIDITY_POOL_PROGRAM_ID_V3,
  LIQUIDITY_POOL_PROGRAM_ID_V4,
  SERUM_PROGRAM_ID_V2,
  SERUM_PROGRAM_ID_V3
} from './ids'
import { LP_TOKENS, NATIVE_SOL, TokenInfo, TOKENS } from './tokens'


export interface AmmInfo {
  name: string;
  coin: TokenInfo;
  pc: TokenInfo;
  lp: TokenInfo;

  version: number;
  programId: string;

  ammId: string;
  ammAuthority: string;
  ammOpenOrders: string;
  ammTargetOrders: string;
  ammQuantities: string;

  poolCoinTokenAccount: string;
  poolPcTokenAccount: string;
  poolWithdrawQueue: string;
  poolTempLpTokenAccount: string;

  serumProgramId: string;
  serumMarket: string;
  serumBids?: string;
  serumAsks?: string;
  serumEventQueue?: string;
  serumCoinVaultAccount: string;
  serumPcVaultAccount: string;
  serumVaultSigner: string;

  official: boolean;

  status?: number;
  currentK?: number;
}


export const AMMS: {string:PoolInfo} = {

  "6gpZ9JkLoYvpA5cwdyPZFsDw6tkbPyyXM5FqRqHxMCny": {
    name: 'mSOL-RAY',
    coin: {...TOKENS.mSOL},
    pc: {...TOKENS.RAY},
    lp: {...LP_TOKENS['mSOL-RAY-V4']},
    version: 4,
    programId: LIQUIDITY_POOL_PROGRAM_ID_V4,
    ammId: '6gpZ9JkLoYvpA5cwdyPZFsDw6tkbPyyXM5FqRqHxMCny',
    ammAuthority: '5Q544fKrFoe6tsEbD7S8EmxGTJYAKtTVhAW5Q5pge4j1',
    ammOpenOrders: 'HDsF9Mp9w3Pc8ZqQJw3NBvtC795NuWENPmTed1YVz5a3',
    ammTargetOrders: '68g1uhKVVLFG1Aua1BKtCx3uiwPixue1qqbKDJAc32Uo',
    // no need
    ammQuantities: NATIVE_SOL.mintAddress,
    poolCoinTokenAccount: 'BusJVbHEkJeYRpHkqCrt85d1LALS1EVcKRjqRFZtBSty',
    poolPcTokenAccount: 'GM1CjxKixFkKpakxx5Lg9u3zYjXAK2Gr2pzoy1G88Td5',
    poolWithdrawQueue: 'GDZx8SZSYsRKc1WfWfbqR9JaTdBEwHwAMcJuYk2rBm74',
    poolTempLpTokenAccount: 'EdLjP9p2AA7zKWwRPxKx8SKFCJ9awfSxnsPgURX6HuuJ',
    serumProgramId: SERUM_PROGRAM_ID_V3,
    serumMarket: 'HVFpsSP4QsC8gFfsFWwYcdmvt3FepDRB6xdFK2pSQtMr',
    serumBids: '7ZCucutxHFwJjfUmxD1Pae8vYg9HB1WQ6DhRkueNyJqF',
    serumAsks: '6cM5rqTHhngGtifjK7pUwved3CdHKZgFj7nnP3LsP325',
    serumEventQueue: 'Gucy2LXDFjWBZEFX4gyrqr6xEb2AWRf4VVgqX33ZXkWu',
    serumCoinVaultAccount: 'GPksxJSxy5pEigdtSLBBZuRQEuGPJRT2ah3J1HwMeKm5',
    serumPcVaultAccount: 'TACxu78UJHz2Vzg2HwGa2w9mvLw2mY5mL7Q3ho9W6J9',
    serumVaultSigner: 'FD6U73ZW2YkD9R8cbDT6KSamVodYqWJBtS3ZcPeU7X29',
    official: true
  },
  "2kPA9XUuHUifcCYTnjSuN7ZrC3ma8EKPrtzUhC86zj3m": {
    name: 'MNDE-mSOL',
    coin: {...TOKENS.MNDE},
    pc: {...TOKENS.mSOL},
    lp: {...LP_TOKENS['MNDE-mSOL-V4']},
    version: 4,
    programId: LIQUIDITY_POOL_PROGRAM_ID_V4,
    ammId: '2kPA9XUuHUifcCYTnjSuN7ZrC3ma8EKPrtzUhC86zj3m',
    ammAuthority: '5Q544fKrFoe6tsEbD7S8EmxGTJYAKtTVhAW5Q5pge4j1',
    ammOpenOrders: 'G3qeShDT2w3Y9XnJbk5TZsx1qbxkBLFmRsnNVLMnkNZb',
    ammTargetOrders: 'DfMpzNeT4XHs2xtN74j5q94QfqPSJbng5BgGyyyChsVm',
    // no need
    ammQuantities: NATIVE_SOL.mintAddress,
    poolCoinTokenAccount: 'F1zwWuPfYLZfLykDYUqu43A74TUsv8mHuWL6BUrwVhL7',
    poolPcTokenAccount: 'TuT7ftAgCQGsETei4Q4nMBwp2QLcDwKnixAEgFSBuao',
    poolWithdrawQueue: '5FoP78mNninxP5VbSHN3LfsBBbqMNqiucANGQungGJLV',
    poolTempLpTokenAccount: '2UbzfMCHjSERpMo9C3BAq5NUhVF9sx39ruJ1zu8Gf4Lu',
    serumProgramId: SERUM_PROGRAM_ID_V3,
    serumMarket: 'AVxdeGgihchiKrhWne5xyUJj7bV2ohACkQFXMAtpMetx',
    serumBids: '9YBjtad6ZxR7hxNXyTjRRPnPgS7geiBMHbBp4BqHsgV2',
    serumAsks: '8UZpvreCr8bprUwstHMPb1pe5jQY82N9fJ1XLa3oKMXg',
    serumEventQueue: '3eeXmsg8byQEC6Q18NE7MSgSbnAJkxz8KNPbW2zfKyfY',
    serumCoinVaultAccount: 'aj1igzDQNRg18h9yFGvNqMPBfCGGWLDvKDp2NdYh92C',
    serumPcVaultAccount: '3QjiyDAny7ZrwPohN8TecXL4jBwGWoSUe7hzTiX35Pza',
    serumVaultSigner: '6Ysd8CE6KwC7KQYpPD9Ax8B77z3bWRnHt1SVrBM8AYC9',
    official: true
  }
}

