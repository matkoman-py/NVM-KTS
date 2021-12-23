export interface Article {
  _id?: number;
  name?: string;
  makingPrice?: number;
  sellingPrice?: number;
  description?: string;
  type?: string;
  ingredients?: string[];
}

export interface ArticlesDTO {
  articles: Article[];
}

export interface ArticleType {
  name?: String;
  value?: String;
}
