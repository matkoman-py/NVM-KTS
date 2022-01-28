export interface Order {
  id?: number;
  description?: string;
  articles?: number[];
  orderStatus?: string;
  price?: number;
}

export interface OrderStatus {
  name: string;
  value: string;
}

export interface ArticlesAndOrderDTO {
  articles: OrderedArticleWithDTO[];
  orderId: number;
}

export interface OrderedArticleWithDTO {
  articleId?: number;
  description: String;
}
