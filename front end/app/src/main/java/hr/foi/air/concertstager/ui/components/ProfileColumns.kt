package hr.foi.air.concertstager.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hr.foi.air.concertstager.ui.theme.Purple

@Composable
fun VisitorProfileColumn(
    name: String,
    email: String,
    onClickEdit: () ->Unit,
    modifier: Modifier= Modifier
){
    Column(
        modifier = modifier
            .padding(start = 30.dp, end = 30.dp)
            .fillMaxWidth()
            .border(1.dp, Color.Black, RoundedCornerShape(10.dp))
            .background(
                color = Purple,
                shape = RoundedCornerShape(10.dp)
            )
    ) {
        Spacer(modifier = modifier.height(10.dp))
        Row(
            modifier = modifier
                .fillMaxWidth()
                .align(Alignment.Start),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            LabelWithIcon(
                content = name,
                imageVector = Icons.Default.Face)
        }
        Spacer(modifier = modifier.height(10.dp))
        Row(
            modifier = modifier
                .fillMaxWidth()
                .align(Alignment.Start),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            LabelWithIcon(
                content = email,
                imageVector = Icons.Default.Email
            )
        }
        Spacer(modifier = modifier.height(10.dp))
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.End
        ) {
            SmallButtonWithIcon(label = "Edit",
                imageVector = Icons.Default.Create,
                onClick = onClickEdit,
                modifier=modifier
                    .padding(end = 10.dp))
        }
    }
}

@Composable
fun EditableVisitorProfileColumn(
    name: String,
    email: String,
    onNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onSaveClick: () -> Unit,
    onCancelClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(start = 30.dp, end = 30.dp)
            .fillMaxWidth()
            .border(1.dp, Color.Black, RoundedCornerShape(10.dp))
            .background(
                color = Purple,
                shape = RoundedCornerShape(10.dp)
            )
    ) {
        Spacer(modifier = modifier.height(10.dp))
        Row(
            modifier = modifier
                .fillMaxWidth()
                .align(Alignment.Start),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            StyledOneLineTextField(label = "Name", value = name, onValueChange = onNameChange)
        }
        Spacer(modifier = modifier.height(10.dp))
        Row(
            modifier = modifier
                .fillMaxWidth()
                .align(Alignment.Start),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            StyledOneLineTextField(label = "Email", value = email, onValueChange = onEmailChange)
        }


        Spacer(modifier = modifier.height(10.dp))

        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(15.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            SmallButtonWithIcon(label = "Save", imageVector = Icons.Default.Check, onClick = onSaveClick)
            SmallButtonWithIcon(label = "Cancel", imageVector = Icons.Default.Close, onClick = onCancelClick)
        }
    }
}

@Composable
fun PerformerProfileColumn(
    modifier: Modifier = Modifier,
    name: String,
    email: String,
    genre: List<String>? = null,
    albums: List<String>? = null,
    onClickEdit: () -> Unit,
) {
    Column(
        modifier = modifier
            .padding(start = 30.dp, end = 30.dp)
            .fillMaxWidth()
            .border(1.dp, Color.Black, RoundedCornerShape(10.dp))
            .background(
                color = Purple,
                shape = RoundedCornerShape(10.dp)
            )
    ) {
        Spacer(modifier = modifier.height(10.dp))
        Row(
            modifier = modifier
                .fillMaxWidth()
                .align(Alignment.Start),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            LabelWithIcon(
                content = name,
                imageVector = Icons.Default.Face
            )
        }
        Spacer(modifier = modifier.height(10.dp))
        Row(
            modifier = modifier
                .fillMaxWidth()
                .align(Alignment.Start),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
                LabelWithIcon(
                    content = email,
                    imageVector = Icons.Default.Email
                )
        }

        Spacer(modifier = modifier.height(10.dp))

        genre?.let {
            Text(
                text = "Genres:",
                color = Color.White,
                modifier = modifier
                    .padding(start = 10.dp)
            )

            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(10.dp)
            ) {
                items(it) { genreItem ->
                    SmallTextLabel(
                        text = genreItem,
                        modifier = Modifier.padding(end = 4.dp)
                    )
                }
            }
        }

        Spacer(modifier = modifier.height(10.dp))
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(5.dp),
            horizontalArrangement = Arrangement.End
        ) {
            SmallButtonWithIcon(label = "Edit", imageVector = Icons.Default.Create, onClick = onClickEdit)
        }

        albums?.let {
            Text(
                text = "Albums:",
                color = Color.White,
                modifier = modifier
                    .padding(start = 10.dp),
            )

            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(10.dp),
            ) {
                items(it) { albumItem ->
                    AlbumCard(
                        albumName = albumItem
                    )
                    Spacer(modifier = modifier.width(10.dp))
                }
            }
        }

        Spacer(modifier = modifier.height(10.dp))
    }
}
@Composable
fun EditablePerformerProfileColumn(
    name: String,
    email: String,
    genre: List<String>,
    onNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onSaveClick: () -> Unit,
    onCancelClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(start = 30.dp, end = 30.dp)
            .fillMaxWidth()
            .border(1.dp, Color.Black, RoundedCornerShape(10.dp))
            .background(
                color = Purple,
                shape = RoundedCornerShape(10.dp)
            )
    ) {
        Spacer(modifier = modifier.height(10.dp))
        Row(
            modifier = modifier
                .fillMaxWidth()
                .align(Alignment.Start),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            StyledOneLineTextField(label = "Name", value = name, onValueChange = onNameChange)
        }
        Spacer(modifier = modifier.height(10.dp))
        Row(
            modifier = modifier
                .fillMaxWidth()
                .align(Alignment.Start),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            StyledOneLineTextField(label = "Email", value = email, onValueChange = onEmailChange)
        }

        Spacer(modifier = modifier.height(10.dp))

        genre.let {
            Text(
                text = "Genres:",
                color = Color.White,
                modifier = modifier
                    .padding(start = 10.dp)
            )

            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(10.dp)
            ) {
                items(it) { genreItem ->
                    SmallTextLabel(
                        text = genreItem,
                        modifier = Modifier.padding(end = 4.dp)
                    )
                }
            }
        }
        Spacer(modifier = modifier.height(10.dp))

        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            SmallButtonWithIcon(label = "Save", imageVector = Icons.Default.Check, onClick = onSaveClick)
            SmallButtonWithIcon(label = "Cancel", imageVector = Icons.Default.Close, onClick = onCancelClick)
        }
    }
}

@Composable
fun VenueProfileColumn(
    name: String,
    description: String,
    city: String,
    address: String,
    capacity: Int
) {
    Column(
        modifier = Modifier
            .padding(start = 30.dp, end = 30.dp)
            .fillMaxWidth()
            .border(1.dp, Color.Black, RoundedCornerShape(10.dp))
            .background(
                color = Purple,
                shape = RoundedCornerShape(10.dp)
            )
    ) {
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Start),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            LabelWithIcon(
                content = name,
                imageVector = Icons.Default.KeyboardArrowRight
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Start),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            LabelWithIcon(
                content = city,
                imageVector = Icons.Default.LocationOn
            )
        }

        Spacer(modifier = Modifier.height(10.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Start),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            LabelWithIcon(
                content = address,
                imageVector = Icons.Default.Home
            )
        }

        Spacer(modifier = Modifier.height(10.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Start),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            LabelWithIcon(
                content = capacity.toString(),
                imageVector = Icons.Default.KeyboardArrowRight
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
    }
}

@Composable
fun OrganizerProfileColumn(
    name: String,
    email: String,
    contactNumber: String,
    onClickEdit: () ->Unit,
    modifier: Modifier= Modifier
){
    Column(
        modifier = modifier
            .padding(start = 30.dp, end = 30.dp)
            .fillMaxWidth()
            .border(1.dp, Color.Black, RoundedCornerShape(10.dp))
            .background(
                color = Purple,
                shape = RoundedCornerShape(10.dp)
            )
    ) {
        Spacer(modifier = modifier.height(10.dp))
            LabelWithIcon(
                content = name,
                imageVector = Icons.Default.Face,
                Modifier.padding(start = 5.dp)
            )
        LabelWithIcon(
            content = email,
            imageVector = Icons.Default.Email,
            Modifier.padding(start = 5.dp)
        )

        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(5.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            LabelWithIcon(
                content = contactNumber,
                imageVector = Icons.Default.Call
            )
            SmallButtonWithIcon(label = "Edit", imageVector = Icons.Default.Create, onClick = onClickEdit)
        }
    }
}

@Composable
fun EditableOrganizerProfileColumn(
    name: String,
    email: String,
    contactNumber: String,
    onNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onContactNumberChange: (String) -> Unit,
    onSaveClick: () -> Unit,
    onCancelClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(start = 30.dp, end = 30.dp)
            .fillMaxWidth()
            .border(1.dp, Color.Black, RoundedCornerShape(10.dp))
            .background(
                color = Purple,
                shape = RoundedCornerShape(10.dp)
            )
    ) {
        Spacer(modifier = modifier.height(10.dp))
        Row(
            modifier = modifier
                .fillMaxWidth()
                .align(Alignment.Start),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            StyledOneLineTextField(label = "Name", value = name, onValueChange = onNameChange)
        }
        Spacer(modifier = modifier.height(10.dp))
        Row(
            modifier = modifier
                .fillMaxWidth()
                .align(Alignment.Start),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            StyledOneLineTextField(label = "Email", value = email, onValueChange = onEmailChange)
        }

        Spacer(modifier = modifier.height(10.dp))

        Row(
            modifier = modifier
                .fillMaxWidth()
                .align(Alignment.Start),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            StyledOneLineTextField(label = "Contact number", value = contactNumber, onValueChange = onContactNumberChange)
        }
        Spacer(modifier = modifier.height(10.dp))

        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            SmallButtonWithIcon(label = "Save", imageVector = Icons.Default.Check, onClick = onSaveClick)
            SmallButtonWithIcon(label = "Cancel", imageVector = Icons.Default.Close, onClick = onCancelClick)
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ShowProfileColumns(
    modifier: Modifier= Modifier
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .size(75.dp)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ){
        EditableVisitorProfileColumn(
            name = "Patrik",
            email = "patrik@gmail.com",
            onNameChange = {},
            onEmailChange = {},
            onSaveClick = { /*TODO*/ },
            onCancelClick = { /*TODO*/ })
        VisitorProfileColumn("patrik", "patrik123", onClickEdit = {/*TODO*/})
        PerformerProfileColumn(
            name = "Karlo",
            email = "ilitre100@gmail.com",
            genre = listOf("Pop","Pop"),
            albums = listOf("Album 1", "Album 2"),
            onClickEdit = { /*TODO*/ })

        OrganizerProfileColumn(
            name = "Naziv",
            email = "ilitre100",
            contactNumber = "091 877 2234",
            onClickEdit = {})

        EditableOrganizerProfileColumn(
            name = "Naziv",
            email = "ilitre100",
            contactNumber = "091 877 2234",
            onNameChange = {  },
            onEmailChange = {  },
            onContactNumberChange = {},
            onSaveClick = { /*TODO*/ },
            onCancelClick = { /*TODO*/ })
    }


}