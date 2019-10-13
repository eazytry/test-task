По основной задаче:
1) /account/deposit - записать деньги на счет, принимает @accountId и @amount
2) /account/subtract - списать деньги со счета, принимает @accountId и @amount
3) /account/transfer - списать с одного счета, записать на другой счет. Принимает @writeOffAccountId, @depositAccountId,
@amount

Все сущности хранятся в виде целого числа с плавающей точкой, с ограничением в 17 чисел целой части и 2 числа после запятой.
Также есть check constraint balance > 0;

Добавил обработку исключений в RestExceptionHandler и соответствующие ответы в зависимости от выброшенных исключений.
Все кастомные исключение хранятся в пакете exception.

Все методы в service объявлены Transactional с уровнем изоляции Serializable (правда в h2 он не работает, но на mysql
все отлично)
