export interface OrderedArticle {
  id: number;
  articleId?: number;
  status: string;
  description?: string;
  articleName?: String;
  articleDescription?: String;
  price?: number;
  image?: String;
  type?: String;
}

export interface ArticleStatus {
  name: string;
  value: string;
}
