package ru.rubicon.roma.picturefilters;

/**
 * Created by roma on 01.03.2017.
 */

public class PullInOutFilter extends TransitionFilterImpl {
    private PullInOutFilter(int framesCount, ActionFilter showFilter, ActionFilter hideFilter) {
        super(framesCount, showFilter, hideFilter);
    }

    public static final TransitionFilter getInstance(int framesCount){
        PullInOutActionFilter pullInOutActionFilter = new PullInOutActionFilter(framesCount);
        SlideInActionFilter slideInActionFilter = new SlideInActionFilter(framesCount, 0.25f);
        pullInOutActionFilter.setNextFilter(slideInActionFilter);
        return new PullInOutFilter(framesCount, pullInOutActionFilter, null);
    }
}
