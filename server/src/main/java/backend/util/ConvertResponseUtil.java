package backend.util;

import common.model.reseponse.paging.NewPagingResponse;
import common.model.reseponse.paging.Pageable;
import common.model.reseponse.paging.SortX;
import common.model.reseponse.post.read.PostReadResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

public class ConvertResponseUtil {

    public static NewPagingResponse<PostReadResponse> pageToPageResponse(Page<PostReadResponse> page) {
        Sort pageableSort = page.getPageable().getSort();
        SortX pageableSortX = new SortX(
                pageableSort.isEmpty(),
                pageableSort.isSorted(),
                pageableSort.isUnsorted()
        );

        Pageable pageable = new Pageable(
                page.getPageable().getOffset(),
                page.getNumber(),
                page.getPageable().getPageSize(),
                Boolean.valueOf(page.getPageable().isPaged()),
                pageableSortX,
                Boolean.valueOf(page.getPageable().isUnpaged())
        );

        Sort sort = page.getSort();
        SortX sortX = new SortX(
                sort.isEmpty(),
                sort.isSorted(),
                sort.isUnsorted()
        );

        return new NewPagingResponse<PostReadResponse>(
                page.getContent(),
                page.isEmpty(),
                page.isFirst(),
                page.isLast(),
                page.getNumber() + 1,
                page.getNumberOfElements(),
                pageable,
                page.getSize(),
                sortX,
                page.getTotalPages(),
                page.getTotalPages()
        );
    }
}
