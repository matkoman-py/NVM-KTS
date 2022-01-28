export interface OrderCreation {
    articles?: number[],
    orderDate?: string,
    deleted?: boolean,
    tableNumber?: number,
    description?: string,
    employeePin?: number
}