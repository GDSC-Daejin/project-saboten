package components

import app.saboten.common.entities.Post
import kotlinx.html.js.onClickFunction
import react.Props
import react.RBuilder
import react.fc
import styled.styledDiv
import utils.component

external interface PostItemProps : Props {
    var post: Post
    var onClick: () -> Unit
}

private val postItem = fc<PostItemProps> { props ->
    styledDiv {

        attrs.onClickFunction = { props.onClick() }
    }
}

fun RBuilder.PostItem(post: Post, onClick: () -> Unit) {
    component(postItem) { props ->
        props.post = post
        props.onClick = onClick
    }
}