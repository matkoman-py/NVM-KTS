export interface ArticleProfit {
    articleProfits: Map<number, ProfitDTO>; 
}

export interface ProfitDTO {
    articleName: string,
    earning: number,
    numberOfOrders: number,
    totalMakingPrice: number,
    totalSellingPrice: number,
}