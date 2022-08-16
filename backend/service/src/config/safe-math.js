import BigNumber from 'bignumber.js'

export interface TokenAmount {
  wei: BigNumber;

  decimals: number;
  _decimals: BigNumber;

}
