//package Data
//import Models.Transaction
//import java.io.*
//
//    object TransactionFileHelper {
//
//        fun readTransactions(file: File): MutableList<Transaction> {
//            if (!file.exists()) return mutableListOf()
//            return try {
//                ObjectInputStream(file.inputStream()).use {
//                    it.readObject() as MutableList<Transaction>
//                }
//            } catch (e: Exception) {
//                mutableListOf()
//            }
//        }
//
//        fun writeTransactions(file: File, transactions: List<Transaction>) {
//            ObjectOutputStream(file.outputStream()).use {
//                it.writeObject(transactions)
//            }
//        }
//    }
//
//
