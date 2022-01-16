import { Ingredient } from './ingredient';

export interface Article {
  id?: number;
  name?: String;
  makingPrice?: number;
  sellingPrice?: number;
  description?: String;
  type?: String;
  ingredients?: Ingredient[];
  image?: String;
}

export interface ArticlesDTO {
  articles: Article[];
}

export interface ArticleType {
  name: string;
  value: string;
}

export interface DeleteMessageDTO {
  message?: string;
}
