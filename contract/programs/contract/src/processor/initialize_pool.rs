use crate::InitializePool;
use anchor_lang::prelude::*;
use vipers::validate::Validate;

impl<'info> Validate<'info> for InitializePool<'info> {
    fn validate(&self) -> Result<()> {
        // require!(
        //     self.init_authority.key().to_string() == "",
        //     #[msg("No Authority")]
        // );
        Ok(())
    }
}

impl InitializePool<'_> {
    pub fn process(
        ctx: Context<InitializePool>,
        bump: u8,
    ) -> Result<()> {
        let pool = &mut ctx.accounts.pool;
        pool.base = ctx.accounts.base.key();
        
        pool.owner = ctx.accounts.owner.key();
        pool.bump = bump;

        pool.coin_supply = Pubkey::default();
        pool.pc_supply = Pubkey::default();

        Ok(())
    }
}

