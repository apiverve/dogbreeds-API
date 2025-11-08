declare module '@apiverve/dogbreeds' {
  export interface dogbreedsOptions {
    api_key: string;
    secure?: boolean;
  }

  export interface dogbreedsResponse {
    status: string;
    error: string | null;
    data: any;
    code?: number;
  }

  export default class dogbreedsWrapper {
    constructor(options: dogbreedsOptions);

    execute(callback: (error: any, data: dogbreedsResponse | null) => void): Promise<dogbreedsResponse>;
    execute(query: Record<string, any>, callback: (error: any, data: dogbreedsResponse | null) => void): Promise<dogbreedsResponse>;
    execute(query?: Record<string, any>): Promise<dogbreedsResponse>;
  }
}
