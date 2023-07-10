package com.dev_musashi.onetouch

import com.dev_musashi.onetouch.domain.model.Table
import com.dev_musashi.onetouch.domain.usecase.InsertTable
import com.dev_musashi.onetouch.domain.usecase.DeleteTable
import com.dev_musashi.onetouch.domain.usecase.GetTable
import com.dev_musashi.onetouch.domain.usecase.GetTables
import com.dev_musashi.onetouch.uiLayer.home.HomeViewModel
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class HomeViewModelTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val tableTest = mutableListOf<Table>()
    private lateinit var homeViewModel : HomeViewModel
    private lateinit var repository: FakeRepository
    private lateinit var getTables: GetTables
    private lateinit var getTable: GetTable
    private lateinit var insertTable: InsertTable
    private lateinit var deleteTable: DeleteTable

    @Before
    fun setUp(){
        repeat(10) {
            tableTest.add(
                Table(
                    it.toLong(),
                    "$it",
                    "",
                    "$it",
                    "",
                    "$it",
                    "",
                    "$it",
                    "",
                    "",
                    "",
                    "it",
                    it.toLong()
                )
            )
        }

        repository = FakeRepository()
        getTables = GetTables(repository)
        getTable = GetTable(repository)
        insertTable = InsertTable(repository)
        deleteTable = DeleteTable(repository)

        homeViewModel = HomeViewModel(getTables, getTable, insertTable, deleteTable)

        runBlocking {
            tableTest.forEach {
                repository.insertTable(it)
            }
        }

    }

    @Test
    fun `when getTables() is called, then the tables are sorted and updated in the state`() = runTest {
        //when
        delay(2000)
        homeViewModel.getAllTables()

        //then
        val result = homeViewModel.state.value.tableList.sortedBy { it.id }
        assertEquals(tableTest, result)
    }

    @Test
    fun `onNameChangedTest`() {
        val str = "123"
        homeViewModel.onNameChanged(str)
        val result = homeViewModel.state.value.name
        assertEquals(str, result)
    }

    @Test
    fun `when titleButton Clicked, then ch`()  = runTest {
        val table = tableTest[3].name
//        homeViewModel.tableButtonClicked( 3)
        delay(2000)
        val result = homeViewModel.state.value.name
        assertEquals(table, result)
    }

    @Test
    fun addTable() = runTest {
        //given
        homeViewModel.state.value = homeViewModel.state.value.copy(title = "추가")

        //when
        homeViewModel.addTableOkayClicked()
        homeViewModel.getAllTables()

        //then
        val result = repository.tables.find { it.title == "추가" }
        val expected = homeViewModel.state.value.tableList.find { it.title == "추가" }


        assertEquals(expected, result)
    }

}