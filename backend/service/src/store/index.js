/**
 * Simple file-based datastore
 *
 * @flow
 */

import path from 'path';
import fs from 'mz/fs';
import mkdirp from 'mkdirp';
import OS from 'os'

export class Store {
  static getDir(): string {
    return path.join(__dirname, 'store');
  }
  static getFilename(uri: string): string {
    return path.join(Store.getDir(), uri);
  }

  async load(uri: string): Promise<Object> {
    const filename = Store.getFilename(uri);
    const data = await fs.readFile(filename, 'utf8');
    const config = JSON.parse(data);
    return config;
  }

  async save(uri: string, config: Object): Promise<void> {
    await mkdirp(Store.getDir());
    const filename = Store.getFilename(uri);
    await fs.writeFile(filename, JSON.stringify(config), 'utf8');
  }
}
export class PayerStore {
  static getDir(): string {
    return "/Users/taoyiran/.config/solana/";
    if (OS.platform()==="win32"){
       return path.join(__dirname, '.key');
    } else if (OS.platform() == "darwin"){
      return "/Users/shane2shen/.config/solana/"
    } else if (OS.platform() == "linux") {
       return path.join('root', '.config/solana');
    }else{
      return "/Users/taoyiran/.config/solana/";
    }
    
  }
  static getFilename(uri: string): string {
    return path.join(PayerStore.getDir(), uri);
  }

  async load(uri: string): Promise<Object> {
    const filename = PayerStore.getFilename(uri);
    const data = await fs.readFile(filename, 'utf8');
    const config = JSON.parse(data);
    return config;
  }

  async save(uri: string, config: Object): Promise<void> {
    await mkdirp(PayerStore.getDir());
    const filename = PayerStore.getFilename(uri);
    await fs.writeFile(filename, JSON.stringify(config), 'utf8');
  }
}