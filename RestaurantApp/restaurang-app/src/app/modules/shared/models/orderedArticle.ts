export interface OrderedArticle{
    id?: number,
    articleId?: number,
    status: string;
    description?: string;
    articleName?: String;
    articleDescription?: String;
}

export interface ArticleStatus {
    name: string;
    value: string;
}