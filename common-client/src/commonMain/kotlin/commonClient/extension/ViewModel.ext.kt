package commonClient.extension

import commonClient.presentation.UnidirectionalViewModelDelegate

expect class ViewModelComponent<S, EF, E>


expect fun <S : Any, EF, E> UnidirectionalViewModelDelegate<S, EF, E>.extract(): ViewModelComponent<S?, EF, E>