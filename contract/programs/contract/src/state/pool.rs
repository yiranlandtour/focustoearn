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


    pub bump: u8,



}

impl Pool {

}
