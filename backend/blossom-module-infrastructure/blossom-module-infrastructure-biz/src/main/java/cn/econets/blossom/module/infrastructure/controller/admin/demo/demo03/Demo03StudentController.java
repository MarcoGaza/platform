package cn.econets.blossom.module.infrastructure.controller.admin.demo.demo03;

import cn.econets.blossom.framework.common.pojo.CommonResult;
import cn.econets.blossom.framework.common.pojo.PageParam;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.framework.common.util.object.BeanUtils;
import cn.econets.blossom.framework.excel.core.util.ExcelUtils;
import cn.econets.blossom.framework.operatelog.core.annotations.OperateLog;
import cn.econets.blossom.module.infrastructure.controller.admin.demo.demo03.vo.Demo03StudentPageReqVO;
import cn.econets.blossom.module.infrastructure.controller.admin.demo.demo03.vo.Demo03StudentRespVO;
import cn.econets.blossom.module.infrastructure.controller.admin.demo.demo03.vo.Demo03StudentSaveReqVO;
import cn.econets.blossom.module.infrastructure.dal.dataobject.demo.demo03.Demo03CourseDO;
import cn.econets.blossom.module.infrastructure.dal.dataobject.demo.demo03.Demo03GradeDO;
import cn.econets.blossom.module.infrastructure.dal.dataobject.demo.demo03.Demo03StudentDO;
import cn.econets.blossom.module.infrastructure.service.demo.demo03.Demo03StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

import static cn.econets.blossom.framework.common.pojo.CommonResult.success;
import static cn.econets.blossom.framework.operatelog.core.enums.OperateTypeEnum.EXPORT;

@Tag(name = "Management Backend - Student")
@RestController
@RequestMapping("/infra/demo03-student")
@Validated
public class Demo03StudentController {

    @Resource
    private Demo03StudentService demo03StudentService;

    @PostMapping("/create")
    @Operation(summary = "Create student")
    @PreAuthorize("@ss.hasPermission('infra:demo03-student:create')")
    public CommonResult<Long> createDemo03Student(@Valid @RequestBody Demo03StudentSaveReqVO createReqVO) {
        return success(demo03StudentService.createDemo03Student(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "Update students")
    @PreAuthorize("@ss.hasPermission('infra:demo03-student:update')")
    public CommonResult<Boolean> updateDemo03Student(@Valid @RequestBody Demo03StudentSaveReqVO updateReqVO) {
        demo03StudentService.updateDemo03Student(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "Delete student")
    @Parameter(name = "id", description = "Number", required = true)
    @PreAuthorize("@ss.hasPermission('infra:demo03-student:delete')")
    public CommonResult<Boolean> deleteDemo03Student(@RequestParam("id") Long id) {
        demo03StudentService.deleteDemo03Student(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "Get students")
    @Parameter(name = "id", description = "Number", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('infra:demo03-student:query')")
    public CommonResult<Demo03StudentRespVO> getDemo03Student(@RequestParam("id") Long id) {
        Demo03StudentDO demo03Student = demo03StudentService.getDemo03Student(id);
        return success(BeanUtils.toBean(demo03Student, Demo03StudentRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "Get student pages")
    @PreAuthorize("@ss.hasPermission('infra:demo03-student:query')")
    public CommonResult<PageResult<Demo03StudentRespVO>> getDemo03StudentPage(@Valid Demo03StudentPageReqVO pageReqVO) {
        PageResult<Demo03StudentDO> pageResult = demo03StudentService.getDemo03StudentPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, Demo03StudentRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "Export students Excel")
    @PreAuthorize("@ss.hasPermission('infra:demo03-student:export')")
    @OperateLog(type = EXPORT)
    public void exportDemo03StudentExcel(@Valid Demo03StudentPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<Demo03StudentDO> list = demo03StudentService.getDemo03StudentPage(pageReqVO).getList();
        // Export Excel
        ExcelUtils.write(response, "Student.xls", "Data", Demo03StudentRespVO.class,
                        BeanUtils.toBean(list, Demo03StudentRespVO.class));
    }

    // ==================== Subtable（Student Courses） ====================

    @GetMapping("/demo03-course/page")
    @Operation(summary = "Get student course paging")
    @Parameter(name = "studentId", description = "Student Number")
    @PreAuthorize("@ss.hasPermission('infra:demo03-student:query')")
    public CommonResult<PageResult<Demo03CourseDO>> getDemo03CoursePage(PageParam pageReqVO,
                                                                        @RequestParam("studentId") Long studentId) {
        return success(demo03StudentService.getDemo03CoursePage(pageReqVO, studentId));
    }

    @PostMapping("/demo03-course/create")
    @Operation(summary = "Create student courses")
    @PreAuthorize("@ss.hasPermission('infra:demo03-student:create')")
    public CommonResult<Long> createDemo03Course(@Valid @RequestBody Demo03CourseDO demo03Course) {
        return success(demo03StudentService.createDemo03Course(demo03Course));
    }

    @PutMapping("/demo03-course/update")
    @Operation(summary = "Update student courses")
    @PreAuthorize("@ss.hasPermission('infra:demo03-student:update')")
    public CommonResult<Boolean> updateDemo03Course(@Valid @RequestBody Demo03CourseDO demo03Course) {
        demo03StudentService.updateDemo03Course(demo03Course);
        return success(true);
    }

    @DeleteMapping("/demo03-course/delete")
    @Parameter(name = "id", description = "Number", required = true)
    @Operation(summary = "Delete student courses")
    @PreAuthorize("@ss.hasPermission('infra:demo03-student:delete')")
    public CommonResult<Boolean> deleteDemo03Course(@RequestParam("id") Long id) {
        demo03StudentService.deleteDemo03Course(id);
        return success(true);
    }

    @GetMapping("/demo03-course/get")
    @Operation(summary = "Get student courses")
    @Parameter(name = "id", description = "Number", required = true)
    @PreAuthorize("@ss.hasPermission('infra:demo03-student:query')")
    public CommonResult<Demo03CourseDO> getDemo03Course(@RequestParam("id") Long id) {
        return success(demo03StudentService.getDemo03Course(id));
    }

    @GetMapping("/demo03-course/list-by-student-id")
    @Operation(summary = "Get student course list")
    @Parameter(name = "studentId", description = "Student Number")
    @PreAuthorize("@ss.hasPermission('infra:demo03-student:query')")
    public CommonResult<List<Demo03CourseDO>> getDemo03CourseListByStudentId(@RequestParam("studentId") Long studentId) {
        return success(demo03StudentService.getDemo03CourseListByStudentId(studentId));
    }

    // ==================== Subtable（Student Class） ====================

    @GetMapping("/demo03-grade/page")
    @Operation(summary = "Get student class pages")
    @Parameter(name = "studentId", description = "Student Number")
    @PreAuthorize("@ss.hasPermission('infra:demo03-student:query')")
    public CommonResult<PageResult<Demo03GradeDO>> getDemo03GradePage(PageParam pageReqVO,
                                                                      @RequestParam("studentId") Long studentId) {
        return success(demo03StudentService.getDemo03GradePage(pageReqVO, studentId));
    }

    @PostMapping("/demo03-grade/create")
    @Operation(summary = "Create a student class")
    @PreAuthorize("@ss.hasPermission('infra:demo03-student:create')")
    public CommonResult<Long> createDemo03Grade(@Valid @RequestBody Demo03GradeDO demo03Grade) {
        return success(demo03StudentService.createDemo03Grade(demo03Grade));
    }

    @PutMapping("/demo03-grade/update")
    @Operation(summary = "Update student classes")
    @PreAuthorize("@ss.hasPermission('infra:demo03-student:update')")
    public CommonResult<Boolean> updateDemo03Grade(@Valid @RequestBody Demo03GradeDO demo03Grade) {
        demo03StudentService.updateDemo03Grade(demo03Grade);
        return success(true);
    }

    @DeleteMapping("/demo03-grade/delete")
    @Parameter(name = "id", description = "Number", required = true)
    @Operation(summary = "Delete student class")
    @PreAuthorize("@ss.hasPermission('infra:demo03-student:delete')")
    public CommonResult<Boolean> deleteDemo03Grade(@RequestParam("id") Long id) {
        demo03StudentService.deleteDemo03Grade(id);
        return success(true);
    }

    @GetMapping("/demo03-grade/get")
    @Operation(summary = "Get student class")
    @Parameter(name = "id", description = "Number", required = true)
    @PreAuthorize("@ss.hasPermission('infra:demo03-student:query')")
    public CommonResult<Demo03GradeDO> getDemo03Grade(@RequestParam("id") Long id) {
        return success(demo03StudentService.getDemo03Grade(id));
    }

    @GetMapping("/demo03-grade/get-by-student-id")
    @Operation(summary = "Get student class")
    @Parameter(name = "studentId", description = "Student Number")
    @PreAuthorize("@ss.hasPermission('infra:demo03-student:query')")
    public CommonResult<Demo03GradeDO> getDemo03GradeByStudentId(@RequestParam("studentId") Long studentId) {
        return success(demo03StudentService.getDemo03GradeByStudentId(studentId));
    }

}
