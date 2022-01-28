import { OrderedArticleWithDTO } from './order';

export interface OrderCreation {
  articlesWithDescription?: OrderedArticleWithDTO[];
  orderDate?: string;
  deleted?: boolean;
  tableNumber?: number;
  description?: string;
  employeePin?: number;
}
