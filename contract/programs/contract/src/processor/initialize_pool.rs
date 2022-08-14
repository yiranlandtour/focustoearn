use crate::InitializePool;
use anchor_lang::prelude::*;
use vipers::validate::Validate;
use crate::processor::VERSION;
use crate::error::ErrorCode;

impl<'info> Validate<'info> for InitializePool<'info> {
    fn validate(&self) -> Result {
        require!(
            self.init_authority.key().to_string() == "",
            ErrorCode::NoAuthority
        );
        Ok(())
    }
}

impl InitializePool<'_> {
    pub fn process(
        ctx: Context<InitializePool>,
        coin_mint_decimal:u8,
        pc_mint_decimal:u8,
        bump: u8,
    ) -> Result {
        let pool = &mut ctx.accounts.pool;
        pool.version = VERSION;
        pool.base = ctx.accounts.base.key();
        pool.is_farm = false;
        pool.owner = ctx.accounts.owner.key();
        pool.bump = bump;
        pool.lp_price_account = ctx.accounts.lp_price_account.key();
        pool.amm_id = ctx.accounts.amm_id.key();
        pool.lp_mint = ctx.accounts.lp_mint.key();

        pool.coin_supply = Pubkey::default();
        pool.pc_supply = Pubkey::default();
        pool.farm_pool_id = Pubkey::default();
        pool.farm_ledger = Pubkey::default();
        pool.lp_supply = ctx.accounts.lp_supply.key();
        pool.add_lp_withdraw_amount_authority =
            ctx.accounts.add_lp_withdraw_amount_authority.key();
        pool.coin_mint_price= ctx.accounts.coin_mint_price.key();
        pool.coin_mint_decimal = coin_mint_decimal;
        pool.pc_mint_price=ctx.accounts.pc_mint_price.key();
        pool.pc_mint_decimal=pc_mint_decimal;
        pool.amm_open_orders= ctx.accounts.amm_open_orders.key();
        pool.amm_coin_mint_supply= ctx.accounts.amm_coin_mint_supply.key();
        pool.amm_pc_mint_supply= ctx.accounts.amm_pc_mint_supply.key();
        Ok(())
    }
}

