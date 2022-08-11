"use strict";
exports.__esModule = true;
exports.mintToken = void 0;
var web3_js_1 = require("@solana/web3.js"); // eslint-disable-line @typescript-eslint/no-unused-vars
var programId_1 = require("../programId");
function mintToken(accounts) {
    var keys = [
        { pubkey: accounts.mint, isSigner: false, isWritable: true },
        { pubkey: accounts.tokenProgram, isSigner: false, isWritable: false },
        { pubkey: accounts.tokenAccount, isSigner: false, isWritable: true },
        { pubkey: accounts.authority, isSigner: false, isWritable: true },
    ];
    var identifier = Buffer.from([172, 137, 183, 14, 207, 110, 234, 56]);
    var data = identifier;
    var ix = new web3_js_1.TransactionInstruction({ keys: keys, programId: programId_1.PROGRAM_ID, data: data });
    return ix;
}
exports.mintToken = mintToken;
