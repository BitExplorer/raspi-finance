package finance.services


import finance.models.Transaction
import finance.repositories.MongoTransactionRepository
import finance.repositories.TransactionRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.util.ArrayList
import java.util.function.Consumer

@Service
open class TransactionService {
    private val LOGGER = LoggerFactory.getLogger(this.javaClass)

    @Autowired
    lateinit private var transactionRepository: TransactionRepository<Transaction>

    @Autowired(required=false)
    internal var mongoTransactionRepository: MongoTransactionRepository? = null

    fun findAllTransactions(pageable: Pageable) : Page<Transaction> {
        //val pageable :Pageable = PageRequest(pageNumber, pageSize, Sort.unsorted())
        val transactions : Page<Transaction> = transactionRepository.findAll(pageable)
        return transactions

    }

    fun findAll(): List<Transaction> {
        val transactions = ArrayList<Transaction>()

        this.transactionRepository.findAll().forEach(Consumer<Transaction> { transactions.add(it) })
        if (transactions.isEmpty() == true ) {
            //TODO: failure
        }
        return transactions
    }

    //fun fetchAccoutTotals(accountNameOwner: String): Double {
    //    return transactionRepository!!.fetchAccoutTotals(accountNameOwner)
    //}

    fun deleteByGuid(guid: String) {
        try {
            transactionRepository.deleteByGuid(guid)
        } catch (ex: Exception) {
            LOGGER.info(ex.message)
        }
    }

    //TODO: Debating on how to pass the updated fields to this method
    fun updateTransaction(guid: String, list: Map<String, String>) {
        val transaction: Transaction = findByGuid(guid);

        transaction.cleared = list["cleared"]!!.toInt()
        transactionRepository.saveAndFlush(transaction)
    }

    fun insertTransaction(transaction: Transaction) {
        val transactionId: Long = transaction.transactionId;
        if (transactionId != 0L ) {
            val transactionFound = transactionRepository.findByTransactionId(transactionId)
            if( transactionFound.transactionId != 0L ) {
                transactionRepository.saveAndFlush(transaction)
            } else {
                LOGGER.info("INFO: transactionRepository found duplicate.")
            }
        }
    }

    fun findByTransactionId(transactionId : Long): Transaction {
        val transaction: Transaction = transactionRepository.findByTransactionId(transactionId)
        if( transaction.transactionId == 0L ) {
            //TODO: failure
        }
        return transaction
    }

    fun findByGuid(guid: String): Transaction {
        val transaction: Transaction = transactionRepository.findByGuid(guid)
        if( transaction.transactionId == 0L ) {
            //TODO: failure
        }
        return transaction
    }

    fun findByAccountNameOwnerIgnoreCaseOrderByTransactionDate(accountNameOwner: String): List<Transaction> {
        val transactions: List<Transaction> = transactionRepository.findByAccountNameOwnerIgnoreCaseOrderByTransactionDate(accountNameOwner)
        if( transactions.isEmpty() ) {
            LOGGER.error("an empty list of AccountNameOwner.")
            //TODO: failure
        }
        return transactions;
        //return transactionRepository.findByAccountNameOwnerIgnoreCaseOrderByTransactionDate(accountNameOwner)
    }
}
