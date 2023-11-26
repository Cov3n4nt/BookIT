package com.covenant.bookit.ui.theme.Components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material3.Card
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.covenant.bookit.Model.Books
import com.covenant.bookit.Model.Sample
import com.covenant.bookit.ui.theme.BookITTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookCard(
    book: Books,
    onRemove: () -> Unit,
    onView: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val dismissState = rememberDismissState(
        confirmValueChange = {direction->
            if(direction == DismissValue.DismissedToEnd){
                onRemove()
            }
            true
        }
    )
    SwipeToDismiss(
        state = dismissState,
        directions = setOf(DismissDirection.StartToEnd),
        background = {},
        dismissContent = {
            Card(
                modifier = modifier,
                onClick = onView,
            ) {
                Surface(color = MaterialTheme.colorScheme.primaryContainer) {
                    Column {
                        Row(modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth(),
                            Arrangement.Start) {
                            Box(modifier = Modifier
                                .padding(8.dp)
                                .align(Alignment.CenterVertically)){
                                Icon(
                                    imageVector = Icons.Default.Book,
                                    contentDescription = "Book",
                                    modifier = Modifier.size(34.dp)
                                )
                            }

                            Column(modifier = Modifier.fillMaxWidth()) {
                                Text(
                                    text = book.title,
                                    overflow = TextOverflow.Ellipsis,
                                    maxLines = 1,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp
                                )
                                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                    Text(
                                        text = book.author.replaceRange(if(book.author.length>10) 10
                                        else book.author.length, book.author.length,if(book.author.length>10)"..." else "")
                                                + " " + book.publishDate.year.toString(),
                                        maxLines = 1)
                                }
                            }

                        }
                        LinearProgressIndicator(
                            progress =  book.pagesRead.toFloat() / book.pages!!.toFloat(),
                            modifier = Modifier
                                .padding(4.dp)
                                .fillMaxWidth(),
                            color = MaterialTheme.colorScheme.primary,
                            trackColor = MaterialTheme.colorScheme.primaryContainer,
                        )
                    }
                }
            }
        }
    )

}

@Preview
@Composable
fun BookCardPreview() {
    BookITTheme {
        BookCard(book = Sample.book, onRemove = { }, onView = { })
    }
}