package com.kgeorgiev.stackoverflowusers.data.network

import androidx.sqlite.SQLiteException
import com.kgeorgiev.stackoverflowusers.domain.error.AppError
import com.kgeorgiev.stackoverflowusers.domain.error.AppException
import kotlinx.coroutines.CancellationException
import retrofit2.HttpException
import java.io.IOException

object RequestHelper {
    suspend fun <T> request(block: suspend () -> T): T {
        try {
            return block()
        } catch (e: AppException) {
            throw e
        } catch (e: CancellationException) {
            throw e
        } catch (e: IOException) {
            throw AppException(AppError.Network, e)
        } catch (e: HttpException) {
            throw AppException(AppError.Server, e)
        } catch (e: SQLiteException) {
            throw AppException(AppError.LocalStorage, e)
        } catch (e: Exception) {
            throw AppException(AppError.Unknown, e)
        }
    }
}