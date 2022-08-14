use anchor_lang::prelude::*;
pub use borsh::BorshDeserialize;
pub use borsh::BorshSerialize;

pub const STALE_AFTER_SLOTS_ELAPSED: u64 = 1;
#[account]
#[derive(Default, Debug)]
pub struct Pool {
    pub version:u8,
    pub base:Pubkey,
    pub owner:Pubkey,
    pub pending_owner:Pubkey,

    pub amm_id:Pubkey,
    pub amm_version:u8,
    pub lp_mint:Pubkey,
    pub lp_supply:Pubkey,
    pub coin_supply:Pubkey,
    pub pc_supply:Pubkey,
    pub add_lp_withdraw_amount_authority:Pubkey,
    pub compound_authority:Pubkey,
    pub coin_mint_price:Pubkey,
    pub coin_mint_decimal: u8,
    pub pc_mint_price:Pubkey,
    pub pc_mint_decimal:u8,
    pub amm_open_orders:Pubkey,
    pub amm_coin_mint_supply:Pubkey,
    pub amm_pc_mint_supply:Pubkey,

    pub bump: u8,
    pub lp_price_account:Pubkey,

    pub is_farm:bool,
    pub farm_pool_id:Pubkey,
    pub farm_pool_version:u8,
    pub farm_ledger:Pubkey,
    pub reward_supply:Vec<Pubkey>,

    pub pending_lp_amount:u64,


}

impl Pool {

}
