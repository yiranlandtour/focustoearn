import { cloneDeep } from 'lodash-es'

import { STAKE_PROGRAM_ID, STAKE_PROGRAM_ID_V4, STAKE_PROGRAM_ID_V5 } from '@/utils/ids'
import { LP_TOKENS, TokenInfo, TOKENS } from '@/utils/tokens'

export interface FarmInfo {
  name: string
  lp: TokenInfo
  reward: TokenInfo
  rewardB?: TokenInfo
  isStake: boolean

  fusion: boolean
  legacy: boolean
  dual: boolean
  version: number
  programId: string

  poolId: string
  poolAuthority: string

  poolLpTokenAccount: string
  poolRewardTokenAccount: string
  poolRewardTokenAccountB?: string

  user?: object
}

export function getFarmByLpMintAddress(lpMintAddress: string): FarmInfo | undefined {
  const farm = FARMS.find((farm) => farm.lp.mintAddress === lpMintAddress)

  if (farm) {
    return cloneDeep(farm)
  }

  return farm
}

export function getFarmByRewardMintAddress(lpMintAddress: string): FarmInfo | undefined {
  const farm = FARMS.find((farm) => farm.reward.mintAddress === lpMintAddress)

  if (farm) {
    return cloneDeep(farm)
  }

  return farm
}

export function getFarmByPoolId(poolId: string): FarmInfo | undefined {
  const farm = FARMS.find((farm) => farm.poolId === poolId)

  if (farm) {
    return cloneDeep(farm)
  }

  return farm
}

export function getAddressForWhat(address: string) {
  // dont use forEach
  for (const farm of FARMS) {
    for (const [key, value] of Object.entries(farm)) {
      // if (key === 'lp') {
      //   if (value.mintAddress === address) {
      //     return { key: 'poolId', poolId: farm.poolId }
      //   }
      // } else if (key === 'reward') {
      //   if (value.mintAddress === address) {
      //     return { key: 'rewardMintAddress', poolId: farm.poolId }
      //   }
      // } else

      if (value === address) {
        return { key, poolId: farm.poolId }
      }
    }
  }

  return {}
}

export const FARMS: {string:FarmInfo} = {
  "7wNhbTS6XQczXs52wcVmfiodRMPfycB3YaG52dWWY6SD": {
    name: 'mSOL-RAY',
    lp: { ...LP_TOKENS['mSOL-RAY-V4'] },
    reward: { ...TOKENS.RAY },
    rewardB: { ...TOKENS.MNDE },
    isStake: false,

    fusion: true,
    legacy: false,
    dual: true,
    version: 5,
    programId: STAKE_PROGRAM_ID_V5,

    poolId: '7wNhbTS6XQczXs52wcVmfiodRMPfycB3YaG52dWWY6SD',
    poolAuthority: '2MbHFiv8H2jjJboqWCaEY1iQh7WFQEwbqNQMYqXUre1p',
    poolLpTokenAccount: '4vyJYQyWusNxCCyFDvWwzjVZFJByAVudWvuTzgHYzwTY', // lp vault
    poolRewardTokenAccount: 'Erz6ai92ieTAqWKHP1tkpGgBKrUJsKe7dhCUyhqtjKRv', // reward vault A
    poolRewardTokenAccountB: 'Ejed9odWtRtNrSndDnrWvu9LaiqCANbkeKHTS3g3H1Xj' // reward vault B
  },

}
