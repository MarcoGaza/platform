package cn.econets.blossom.module.infrastructure.service.demo.demo03;

import cn.econets.blossom.framework.common.pojo.PageParam;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.framework.common.util.object.BeanUtils;
import cn.econets.blossom.module.infrastructure.controller.admin.demo.demo03.vo.Demo03StudentPageReqVO;
import cn.econets.blossom.module.infrastructure.controller.admin.demo.demo03.vo.Demo03StudentSaveReqVO;
import cn.econets.blossom.module.infrastructure.dal.dataobject.demo.demo03.Demo03CourseDO;
import cn.econets.blossom.module.infrastructure.dal.dataobject.demo.demo03.Demo03GradeDO;
import cn.econets.blossom.module.infrastructure.dal.dataobject.demo.demo03.Demo03StudentDO;
import cn.econets.blossom.module.infrastructure.dal.mysql.demo.demo03.Demo03CourseMapper;
import cn.econets.blossom.module.infrastructure.dal.mysql.demo.demo03.Demo03GradeMapper;
import cn.econets.blossom.module.infrastructure.dal.mysql.demo.demo03.Demo03StudentMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.List;

import static cn.econets.blossom.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.econets.blossom.module.infrastructure.enums.ErrorCodeConstants.*;

/**
 * Student Service Implementation class
 *
 *
 */
@Service
@Validated
public class Demo03StudentServiceImpl implements Demo03StudentService {

    @Resource
    private Demo03StudentMapper demo03StudentMapper;
    @Resource
    private Demo03CourseMapper demo03CourseMapper;
    @Resource
    private Demo03GradeMapper demo03GradeMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createDemo03Student(Demo03StudentSaveReqVO createReqVO) {
        // Insert
        Demo03StudentDO demo03Student = BeanUtils.toBean(createReqVO, Demo03StudentDO.class);
        demo03StudentMapper.insert(demo03Student);

        // Insert subtable
        createDemo03CourseList(demo03Student.getId(), createReqVO.getDemo03Courses());
        createDemo03Grade(demo03Student.getId(), createReqVO.getDemo03Grade());
        // Return
        return demo03Student.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateDemo03Student(Demo03StudentSaveReqVO updateReqVO) {
        // Check existence
        validateDemo03StudentExists(updateReqVO.getId());
        // Update
        Demo03StudentDO updateObj = BeanUtils.toBean(updateReqVO, Demo03StudentDO.class);
        demo03StudentMapper.updateById(updateObj);

        // Update subtable
        updateDemo03CourseList(updateReqVO.getId(), updateReqVO.getDemo03Courses());
        updateDemo03Grade(updateReqVO.getId(), updateReqVO.getDemo03Grade());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteDemo03Student(Long id) {
        // Check existence
        validateDemo03StudentExists(id);
        // Delete
        demo03StudentMapper.deleteById(id);

        // Delete subtable
        deleteDemo03CourseByStudentId(id);
        deleteDemo03GradeByStudentId(id);
    }

    private void validateDemo03StudentExists(Long id) {
        if (demo03StudentMapper.selectById(id) == null) {
            throw exception(DEMO03_STUDENT_NOT_EXISTS);
        }
    }

    @Override
    public Demo03StudentDO getDemo03Student(Long id) {
        return demo03StudentMapper.selectById(id);
    }

    @Override
    public PageResult<Demo03StudentDO> getDemo03StudentPage(Demo03StudentPageReqVO pageReqVO) {
        return demo03StudentMapper.selectPage(pageReqVO);
    }

    // ==================== Subtable（Student Courses） ====================

    @Override
    public List<Demo03CourseDO> getDemo03CourseListByStudentId(Long studentId) {
        return demo03CourseMapper.selectListByStudentId(studentId);
    }

    private void createDemo03CourseList(Long studentId, List<Demo03CourseDO> list) {
        if (list != null) {
            list.forEach(o -> o.setStudentId(studentId));
        }
        demo03CourseMapper.insertBatch(list);
    }

    private void updateDemo03CourseList(Long studentId, List<Demo03CourseDO> list) {
        deleteDemo03CourseByStudentId(studentId);
		list.forEach(o -> o.setId(null).setUpdater(null).setUpdateTime(null)); // Solve the update situation：1）id Conflict；2）updateTime Not updated
        createDemo03CourseList(studentId, list);
    }

    private void deleteDemo03CourseByStudentId(Long studentId) {
        demo03CourseMapper.deleteByStudentId(studentId);
    }

    @Override
    public PageResult<Demo03CourseDO> getDemo03CoursePage(PageParam pageReqVO, Long studentId) {
        return demo03CourseMapper.selectPage(pageReqVO, studentId);
    }

    @Override
    public Long createDemo03Course(Demo03CourseDO demo03Course) {
        demo03CourseMapper.insert(demo03Course);
        return demo03Course.getId();
    }

    @Override
    public void updateDemo03Course(Demo03CourseDO demo03Course) {
        demo03CourseMapper.updateById(demo03Course);
    }

    @Override
    public void deleteDemo03Course(Long id) {
        demo03CourseMapper.deleteById(id);
    }

    @Override
    public Demo03CourseDO getDemo03Course(Long id) {
        return demo03CourseMapper.selectById(id);
    }

    // ==================== Subtable（Student Class） ====================

    @Override
    public Demo03GradeDO getDemo03GradeByStudentId(Long studentId) {
        return demo03GradeMapper.selectByStudentId(studentId);
    }

    private void createDemo03Grade(Long studentId, Demo03GradeDO demo03Grade) {
        if (demo03Grade == null) {
            return;
        }
        demo03Grade.setStudentId(studentId);
        demo03GradeMapper.insert(demo03Grade);
    }

    private void updateDemo03Grade(Long studentId, Demo03GradeDO demo03Grade) {
        if (demo03Grade == null) {
			return;
        }
        demo03Grade.setStudentId(studentId);
        demo03Grade.setUpdater(null).setUpdateTime(null); // Solve the update situation：updateTime Not updated
        demo03GradeMapper.insertOrUpdate(demo03Grade);
    }

    private void deleteDemo03GradeByStudentId(Long studentId) {
        demo03GradeMapper.deleteByStudentId(studentId);
    }

    @Override
    public PageResult<Demo03GradeDO> getDemo03GradePage(PageParam pageReqVO, Long studentId) {
        return demo03GradeMapper.selectPage(pageReqVO, studentId);
    }

    @Override
    public Long createDemo03Grade(Demo03GradeDO demo03Grade) {
        // Check if it already exists
        if (demo03GradeMapper.selectByStudentId(demo03Grade.getStudentId()) != null) {
            throw exception(DEMO03_GRADE_EXISTS);
        }
        demo03GradeMapper.insert(demo03Grade);
        return demo03Grade.getId();
    }

    @Override
    public void updateDemo03Grade(Demo03GradeDO demo03Grade) {
        // Check existence
        validateDemo03GradeExists(demo03Grade.getId());
        // Update
        demo03GradeMapper.updateById(demo03Grade);
    }

    @Override
    public void deleteDemo03Grade(Long id) {
        // Check existence
        validateDemo03GradeExists(id);
        // Delete
        demo03GradeMapper.deleteById(id);
    }

    @Override
    public Demo03GradeDO getDemo03Grade(Long id) {
        return demo03GradeMapper.selectById(id);
    }

    private void validateDemo03GradeExists(Long id) {
        if (demo03GradeMapper.selectById(id) == null) {
            throw exception(DEMO03_GRADE_NOT_EXISTS);
        }
    }

}
