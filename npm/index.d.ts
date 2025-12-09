declare module '@apiverve/dogbreeds' {
  export interface dogbreedsOptions {
    api_key: string;
    secure?: boolean;
  }

  export interface dogbreedsResponse {
    status: string;
    error: string | null;
    data: DogBreedsData;
    code?: number;
  }


  interface DogBreedsData {
      breed:       string;
      foundCount:  number;
      foundBreeds: FoundBreed[];
  }
  
  interface FoundBreed {
      name:     string;
      weight:   Weight;
      height:   Height;
      bredFor:  string;
      group:    string;
      lifeSpan: LifeSpan;
      traits:   string[];
  }
  
  interface Height {
      lowerInches: number;
      upperInches: number;
  }
  
  interface LifeSpan {
      lowerYears: number;
      upperYears: number;
  }
  
  interface Weight {
      lowerLbs: number;
      upperLbs: number;
  }

  export default class dogbreedsWrapper {
    constructor(options: dogbreedsOptions);

    execute(callback: (error: any, data: dogbreedsResponse | null) => void): Promise<dogbreedsResponse>;
    execute(query: Record<string, any>, callback: (error: any, data: dogbreedsResponse | null) => void): Promise<dogbreedsResponse>;
    execute(query?: Record<string, any>): Promise<dogbreedsResponse>;
  }
}
