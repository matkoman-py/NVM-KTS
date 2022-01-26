// "id": 11,
//         "deleted": false,
//         "description": "No ketchup",
//         "orderDate": "2021-11-03T12:43:33",
//         "articles": [],
//         "orderedArticles": [],
//         "tableNumber": 1,
//         "employeeId": 3
//         "orderStatus": "NOT_STARTED"

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
  articleIds: number[];
  orderId: number;
}
