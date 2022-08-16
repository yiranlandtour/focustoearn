export interface Swap{
  source_token_account:string,
  dest_token_account:string,
  amm_id:string
}
const BRIDGE_POOL = {
  "bridgePool": "FPzdZTXoADaLfBArQkRXQa4TY5T2CwG2Fa59ATbiqyZL",
  "swap": [
    [
      {
        "source_token_account":"9FsP1rLsNfUrXCAQE2a7WzMr3ksehAomQKLM7iMDjgKP",
        "dest_token_account":"D8wuuJ6TLrgLfFKQECzi25HBcaaS1YemjbRvCGD235pL",
        "amm_id":"6gpZ9JkLoYvpA5cwdyPZFsDw6tkbPyyXM5FqRqHxMCny"
      }
    ],
    [
      {
        "source_token_account":"BZ4R3kUFbudBAXsNt3dPFncL7TbrqrLeVYBBwViUJuAL",
        "dest_token_account":"D8wuuJ6TLrgLfFKQECzi25HBcaaS1YemjbRvCGD235pL",
        "amm_id":"2kPA9XUuHUifcCYTnjSuN7ZrC3ma8EKPrtzUhC86zj3m"
      },
      {
        "source_token_account":"D8wuuJ6TLrgLfFKQECzi25HBcaaS1YemjbRvCGD235pL",
        "dest_token_account":"9FsP1rLsNfUrXCAQE2a7WzMr3ksehAomQKLM7iMDjgKP",
        "amm_id":"6gpZ9JkLoYvpA5cwdyPZFsDw6tkbPyyXM5FqRqHxMCny"
      }
    ]
  ]
}

